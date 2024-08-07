package com.coderman.admin.sync.executor;

import com.alibaba.fastjson.JSON;
import com.coderman.admin.sync.constant.SyncConstant;
import com.coderman.admin.sync.exception.ErrorCodeEnum;
import com.coderman.admin.sync.exception.SyncException;
import com.coderman.admin.sync.plan.meta.MsgTableMeta;
import com.coderman.admin.sync.sql.meta.SqlMeta;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangyukang
 */
@Slf4j
public class JdbcExecutor extends AbstractExecutor {


    @Override
    public List<SqlMeta> execute() throws Throwable {

        final JdbcTemplate jdbcTemplate = super.getJdbcTemplate();


        if (null == jdbcTemplate) {

            throw new SyncException(ErrorCodeEnum.DB_NOT_CONNECT);
        }

        // 非查询集合
        final List<SqlMeta> noSelectList = new ArrayList<>();

        for (SqlMeta sqlMeta : super.getSqlList()) {


            // 处理查询语句
            if (SyncConstant.OPERATE_TYPE_SELECT.equalsIgnoreCase(sqlMeta.getSqlType())) {

                if (sqlMeta.getParamList().size() != 1) {

                    throw new SyncException(ErrorCodeEnum.SQL_PARAM_EXCEED);
                }

                try {

                    // 打印sql日志
                    this.printLog(sqlMeta.getSql(), sqlMeta.getParamList());

                    // 查询数据
                    List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sqlMeta.getSql(), sqlMeta.getParamList().get(0));
                    sqlMeta.setResultList(resultList);

                    log.debug("执行SQL语句返回结果:{}", JSON.toJSONString(resultList));

                } catch (Exception e) {

                    log.error("", e);
                    throw new RuntimeException(String.format("sql:%s,destDb:%s,error:%s", sqlMeta.getSql(),
                            CollectionUtils.isEmpty(sqlMeta.getCallbackTaskList()) ? null : (sqlMeta.getCallbackTaskList().get(0).getProject() + ":" + sqlMeta.getCallbackTaskList().get(0).getDb()), e), e);
                }

            } else {


                if (super.getMsgMeta() != null) {


                    for (MsgTableMeta tableMeta : super.getMsgMeta().getTableMetaList()) {

                        if (tableMeta.getCode().equals(sqlMeta.getTableCode())) {

                            sqlMeta.setAffectNum(tableMeta.getAffectNum());
                            break;
                        }
                    }
                }

                // 封装 insert,update,delete 语句
                noSelectList.add(sqlMeta);

            }
        }

        if (CollectionUtils.isNotEmpty(noSelectList)) {


            TransactionTemplate transactionTemplate = super.getTransactionTemplate();

            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {


                    for (SqlMeta meta : noSelectList) {

                        int[] affects;

                        try {

                            // 打印sql日志
                            printLog(meta.getSql(), meta.getParamList());

                            affects = jdbcTemplate.batchUpdate(meta.getSql(), meta.getParamList(), meta.getArgTypes());

                        } catch (Exception e) {

                            if (e instanceof DuplicateKeyException) {

                                throw new SyncException(ErrorCodeEnum.DB_KEY_DUPLICATE, "键值重复," + e.getMessage());
                            }

                            throw new RuntimeException(String.format("tableCode:%s,msg:%s,目标库:%s,error:%s", meta.getTableCode(), CollectionUtils.isEmpty(meta.getCallbackTaskList()) ?
                                    meta.getUniqueKey() : meta.getCallbackTaskList().get(0).getMsg(), CollectionUtils.isEmpty(meta.getCallbackTaskList()) ? null : (meta.getCallbackTaskList().get(0).getProject() + ":" + meta.getCallbackTaskList().get(0).getDb()), e), e);
                        }

                        int count = 0;

                        for (int affect : affects) {

                            // oracle对于成功的批处理,该数据包含了所有的-2,1或正整数表示
                            if (affect == -2) {

                                affect = 1;
                            }

                            if (affect < 0) {

                                affect = affect * -1;
                            }

                            count += affect;
                        }

                        if (meta.getAffectNum() != null && meta.getAffectNum() > count) {

                            throw new RuntimeException(String.format("计划编号:%s,预计受影响行数:%s,实际受影响行数:%s", meta.getTableCode(), meta.getAffectNum(), count));
                        }

                        meta.setAffectNum(count);
                    }
                }
            });
        }


        return super.getSqlList();
    }


    private void printLog(String sql, List<Object[]> paramList) {
        // 使用 StringBuilder 构建日志信息
        StringBuilder stringBuilder = new StringBuilder();

        // 添加 SQL 语句
        stringBuilder.append("执行 SQL 语句: ")
                .append(sql);

        // 遍历参数列表并格式化
        if (paramList != null && !paramList.isEmpty()) {
            stringBuilder.append("，参数列表: ");

            for (Object[] params : paramList) {
                stringBuilder.append("[")
                        .append(Arrays.stream(params)
                                .map(this::formatParameter)
                                .collect(Collectors.joining(", ")))
                        .append("] ");
            }
        } else {
            stringBuilder.append("，无参数");
        }

        // 打印日志
        log.info(stringBuilder.toString());
    }

    /**
     * 格式化参数，特别是处理 Date 类型
     *
     * @param param 参数
     * @return 格式化后的字符串
     */
    private String formatParameter(Object param) {
        if (param instanceof Date) {
            return DateFormatUtils.format((Date) param, "yyyy-MM-dd HH:mm:ss.SSS");
        }
        return Objects.toString(param);
    }
}
