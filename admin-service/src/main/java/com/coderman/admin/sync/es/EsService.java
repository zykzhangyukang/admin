package com.coderman.admin.sync.es;

import com.coderman.admin.sync.model.ResultModel;
import com.coderman.api.vo.PageVO;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author zhangyukang
 */
public interface EsService {

    /**
     * 批量查询同步记录
     *
     * @param resultModelList 同步记录list
     * @throws IOException
     * @return
     */
    public boolean batchInsertSyncResult(List<ResultModel> resultModelList) throws IOException;

    /**
     * 修改同步结果为成功
     *
     * @param resultModel  消息同步结果
     * @param remark 备注信息
     * @throws IOException
     */
    void updateSyncResultSuccess(ResultModel resultModel, String remark) throws IOException;


    /**
     * 同步记录搜索
     *
     * @param searchSourceBuilder 查询条件
     * @throws IOException
     * @return
     */
    com.coderman.api.vo.ResultVO<PageVO<List<ResultModel>>> searchSyncResult(SearchSourceBuilder searchSourceBuilder) throws IOException;

    /**
     * 批量删除同步记录
     *
     * @param ltTime
     * @param deleteSize
     * @throws IOException
     * @return
     */
    int batchDeleteSyncResult(Date ltTime, int deleteSize) throws IOException;
}
