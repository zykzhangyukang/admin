package com.coderman.admin.sync.es.impl;

import com.alibaba.fastjson.JSON;
import com.coderman.admin.sync.constant.PlanConstant;
import com.coderman.admin.sync.es.EsService;
import com.coderman.admin.sync.model.ResultModel;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.service.anntation.LogError;
import com.coderman.service.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangyukang
 */
@Service
@Slf4j
public class EsServiceImpl implements EsService {

    @Resource
    private RestHighLevelClient restHighLevelClient;
    /**
     * 同步系统索引别名
     */
    private static String ALIAS = "admin_alias";
    /**
     * 索引前缀
     */
    private static String INDEX_PREFIX = "admin_result_";
    /**
     * 当前使用的索引名
     */
    public String syncResultIndexName;


    @Override
    @LogError(value = "批量插入同步记录")
    public boolean batchInsertSyncResult(List<ResultModel> resultModelList) throws IOException {

        boolean result = false;
        long startTime = System.currentTimeMillis();

        BulkRequest bulkRequest = new BulkRequest();
        for (ResultModel resultModel : resultModelList) {
            IndexRequest indexRequest = new IndexRequest(this.syncResultIndexName);
            indexRequest.source(JSON.toJSONString(resultModel), XContentType.JSON);
            indexRequest.id(resultModel.getUuid());
            bulkRequest.add(indexRequest);
        }

        BulkResponse bulkResponse = this.restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (bulkResponse.hasFailures()) {

            log.error("批量插入同步记录到es错误:{}", bulkResponse.buildFailureMessage());

        } else {

            result = true;
            List<String> uuidList = resultModelList.stream().map(ResultModel::getUuid).distinct().collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(uuidList)) {

                // 更新es同步状态
                JdbcTemplate jdbcTemplate = SpringContextUtil.getBean(JdbcTemplate.class);
                NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
                Map<String, Object> params = new HashMap<>(uuidList.size());
                params.put("params", uuidList);
                namedParameterJdbcTemplate.update("update sync_result set sync_To_es = 1 where uuid in (:params)", params);
            }

            log.info("同步记录写入Es, 总数:{}, 耗时:{} ms", uuidList.size(), System.currentTimeMillis() - startTime);
        }

        return result;
    }

    @Override
    @LogError(value = "修改同步结果为成功")
    public void updateSyncResultSuccess(ResultModel resultModel, String remark) throws IOException {

        UpdateByQueryRequest updateByQuery = new UpdateByQueryRequest(this.syncResultIndexName);

        updateByQuery.setRefresh(true);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("msgId", resultModel.getMsgId()))
                .should(QueryBuilders.termQuery("status", PlanConstant.RESULT_STATUS_FAIL));
        updateByQuery.setQuery(boolQueryBuilder);

        updateByQuery.setBatchSize(100);
        updateByQuery.setMaxDocs(100);
        updateByQuery.setScript(
                new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG,
                        "ctx._source.status = 'success';" +
                                "ctx._source.remark = '" + remark + "'", Collections.emptyMap())
        );

        BulkByScrollResponse response = this.restHighLevelClient.updateByQuery(updateByQuery, RequestOptions.DEFAULT);
        if (CollectionUtils.isNotEmpty(response.getBulkFailures())) {
            log.error("批量更新ES同步记录状态失败:{}", JSON.toJSONString(response.getBulkFailures()));
        }

    }

    @Override
    @LogError(value = "同步记录搜索")
    public com.coderman.api.vo.ResultVO<PageVO<List<ResultModel>>> searchSyncResult(SearchSourceBuilder searchSourceBuilder) throws IOException {

        SearchRequest searchRequest = new SearchRequest(ALIAS);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();

        List<ResultModel> list = new ArrayList<>(30);
        for (SearchHit hit : hits) {

            ResultModel resultModel = JSON.parseObject(hit.getSourceAsString(), ResultModel.class);
            list.add(resultModel);

            // 高亮字段设置
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField msgContentField = highlightFields.get("msgContent");
            HighlightField syncContentField = highlightFields.get("syncContent");

            if (msgContentField != null && msgContentField.getFragments() != null) {
                Text[] fragments = msgContentField.getFragments();
                StringBuilder newMsgContent = new StringBuilder();
                for (Text text : fragments) {
                    newMsgContent.append(text);
                }
                resultModel.setHlsMsgContent(newMsgContent.toString());
            }

            if (syncContentField != null && syncContentField.getFragments() != null) {
                Text[] fragments = syncContentField.getFragments();
                StringBuilder newSyncContent = new StringBuilder();
                for (Text text : fragments) {
                    newSyncContent.append(text);
                }
                resultModel.setHlsSyncContent(newSyncContent.toString());
            }
        }

        assert hits.getTotalHits() != null;
        PageVO<List<ResultModel>> pageVO = new PageVO<>(hits.getTotalHits().value, list);
        return ResultUtil.getSuccessPage(ResultModel.class, pageVO);
    }

    @Override
    public int batchDeleteSyncResult(Date ltTime, int limit) throws IOException {

        // 用于计算已删除的文档数量
        int totalDeleted = 0;
        // 每次删除 1000
        int batchSize = 1000;

        while (true) {

            // 计算剩余需要删除的文档数量
            int remainingDelete = limit - totalDeleted;

            // 限制每次处理的文档数量不超过剩余需要删除的数量和设定的删除大小
            int currentDeleteSize = Math.min(batchSize, remainingDelete);

            DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(this.syncResultIndexName);

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.rangeQuery("msgCreateTime").lt(ltTime.getTime()))
                    .should(QueryBuilders.termQuery("status", PlanConstant.RESULT_STATUS_SUCCESS));

            deleteByQueryRequest.setRefresh(true);
            deleteByQueryRequest.setQuery(boolQueryBuilder);
            // 控制每批次处理的文档数量
            deleteByQueryRequest.setBatchSize(currentDeleteSize);
            // 控制每次请求返回的文档数量
            deleteByQueryRequest.setMaxDocs(currentDeleteSize);

            BulkByScrollResponse response = this.restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);

            long deletedDocuments = response.getStatus().getDeleted();

            if (deletedDocuments == 0) {
                break;
            }

            // 更新已删除的文档数量
            totalDeleted += (int) deletedDocuments;

            if (totalDeleted >= limit) {
                // 如果已删除的文档数量达到限制数量，则退出循环
                break;
            }
        }

        return totalDeleted;
    }


    /**
     * 根据环境区分命名空间
     */
    private void initNameSpace() {
        Environment environment = SpringContextUtil.getApplicationContext().getEnvironment();
        String[] profiles = environment.getActiveProfiles();
        String currentEnv = Stream.of(profiles).findFirst().orElse(null);
        if (!"pro".equalsIgnoreCase(currentEnv)) {
            ALIAS = "admin_test_alias";
            INDEX_PREFIX = "admin_test_result_";
        }
    }

    @PostConstruct
    public void init() throws IOException {

        this.initNameSpace();

        GetAliasesRequest getAliasesRequest = new GetAliasesRequest(ALIAS);
        boolean existsAlias = this.restHighLevelClient.indices().existsAlias(getAliasesRequest, RequestOptions.DEFAULT);

        if (!existsAlias) {
            this.createResultModelIndex();
        } else {
            GetAliasesResponse response = this.restHighLevelClient.indices().getAlias(new GetAliasesRequest(EsServiceImpl.ALIAS), RequestOptions.DEFAULT);
            List<Long> list = new ArrayList<>();
            for (Map.Entry<String, Set<AliasMetaData>> entry : response.getAliases().entrySet()) {
                String indexName = entry.getKey();
                if (indexName.matches("^" + INDEX_PREFIX + "\\d{13}$")) {
                    list.add(Long.valueOf(indexName.replace(INDEX_PREFIX, "")));
                }
            }

            list.sort((o1, o2) -> (int) (o2 - o1));
            this.syncResultIndexName = INDEX_PREFIX + list.get(0);
            log.info("当前es工作索引为:{}", this.syncResultIndexName);
        }
    }

    /**
     * 创建索引
     */
    private void createResultModelIndex() throws IOException {

        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject("uuid")
                .field("type", "keyword")
                .endObject()
                .startObject("planUuid")
                .field("type", "keyword")
                .endObject()
                .startObject("planSrc")
                .field("type", "keyword")
                .endObject()
                .startObject("planCode")
                .field("type", "keyword")
                .endObject()
                .startObject("planName")
                .field("type", "text")
                .endObject()
                .startObject("mqId")
                .field("type", "keyword")
                .endObject()
                .startObject("msgId")
                .field("type", "keyword")
                .endObject()
                .startObject("msgContent")
                .field("type", "text")
                .endObject()
                .startObject("srcProject")
                .field("type", "keyword")
                .endObject()
                .startObject("destProject")
                .field("type", "keyword")
                .endObject()
                .startObject("syncContent")
                .field("type", "text")
                .endObject()
                .startObject("msgCreateTime")
                .field("type", "date")
                .field("format", "epoch_second")
                .endObject()
                .startObject("syncTime")
                .field("type", "date")
                .field("format", "epoch_second")
                .endObject()
                .startObject("status")
                .field("type", "keyword")
                .endObject()
                .startObject("errorMsg")
                .field("type", "text")
                .endObject()
                .startObject("repeatCount")
                .field("type", "integer")
                .endObject()
                .startObject("remark")
                .field("type", "text")
                .endObject()
                .endObject()
                .endObject();

        this.syncResultIndexName = INDEX_PREFIX + System.currentTimeMillis();

        // 创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest();
        createIndexRequest.index(this.syncResultIndexName);
        createIndexRequest.alias(new Alias(EsServiceImpl.ALIAS));
        createIndexRequest.mapping("resultModel", xContentBuilder);

        CreateIndexResponse indexResponse = this.restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

        if (!indexResponse.isAcknowledged()) {

            throw new RuntimeException("创建索引失败");
        }
    }

}
