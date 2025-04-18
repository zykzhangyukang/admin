package com.coderman.admin.sync.util;

import com.coderman.admin.sync.exception.ErrorCodeEnum;
import com.coderman.admin.sync.exception.SyncException;
import com.coderman.admin.sync.executor.AbstractExecutor;
import com.coderman.admin.sync.pair.SyncPair;
import com.coderman.admin.sync.sql.meta.SqlMeta;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SqlUtil {


    public static String fillParam(SqlMeta sqlMeta, AbstractExecutor executor) {

        if (null != executor.getMongoTemplate()) {

            String sql = sqlMeta.getSql();
            List<Object> params = new ArrayList<>();

            for (Object[] objects : sqlMeta.getParamList()) {


                for (Object object : objects) {


                    if (object instanceof ObjectId) {

                        String param = "{\"$oid\":\"" + object.toString() + "\"}";
                        params.add(param);

                    } else if (object instanceof Date) {

                        String param = "{\"$date\":" + ((Date) object).getTime() + "}";
                        params.add(param);
                    } else {

                        params.add(object);
                    }
                }

            }

            String[] sqlArray = sql.split("\\?");

            if (sqlArray.length != params.size() + 1) {

                throw new SyncException(ErrorCodeEnum.SQL_PARAM_NUM_NOT_MATCH, "SQL参数异常:" + sql + "[" + StringUtils.join(params, ",") + "]");
            }

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < sqlArray.length -1; i++) {


                if (params.get(i) instanceof String && (!((String) params.get(i)).startsWith("{") || SqlUtil.isMsgContent((String) params.get(i)))) {

                    String tmpStr = (String) params.get(i);

                    if (tmpStr.contains("\"")) {

                        tmpStr = tmpStr.replace("\"", "\\\"");
                    }

                    builder.append(sqlArray[i]).append("\"").append(tmpStr).append("\"");
                } else {

                    builder.append(sqlArray[i]).append(params.get(i));
                }
            }

            builder.append(sqlArray[sqlArray.length - 1]);

            return builder.toString();
        } else {

            return sqlMeta.getSql();
        }
    }


    private static boolean isMsgContent(String msg) {
        return msg.contains("plan") && msg.contains("tables") && msg.contains("unique");
    }


    public static String getFieldName(String field) {


        if ("sync_time".equals(field)) {

            return "syncTime";
        }


        if ("sync_uuid".equals(field)) {

            return "syncUUid";
        }


        if ("src_project".equals(field)) {

            return "srcProject";
        }


        if ("dest_project".equals(field)) {

            return "destProject";
        }


        if ("db_name".equals(field)) {

            return "dbName";
        }


        if ("msg_content".equals(field)) {

            return "msgContent";
        }


        if ("create_time".equals(field)) {

            return "createTime";
        }


        if ("send_time".equals(field)) {

            return "sendTime";
        }

        if ("ack_time".equals(field)) {

            return "ackTime";
        }

        if ("repeat_count".equals(field)) {

            return "repeatCount";
        }

        if ("send_status".equals(field)) {

            return "sendStatus";
        }

        if ("deal_count".equals(field)) {

            return "dealCount";
        }

        if ("deal_status".equals(field)) {

            return "dealStatus";
        }

        return field;
    }

    public static String getCollectionName(String table) {

        if ("pub_callback".equals(table)) {

            return "callbackModel";
        } else if ("pub_mq_message".equals(table)) {

            return "mqMessageModel";
        }

        return table;
    }

    public static List<String> makeMongoSql(List<SyncPair<String, Integer>> inList, List<String> eqList, List<String> ltList) {

        List<String> whereStrList = new ArrayList<>();


        if (CollectionUtils.isNotEmpty(inList)) {


            for (SyncPair<String, Integer> param : inList) {

                String replaceStr = StringUtils.repeat("?", ",", param.getValue());
                whereStrList.add("{\"" + SqlUtil.getFieldName(param.getKey()) + "\":{\"$in\":[" + replaceStr + "]}}");
            }

        }

        if (CollectionUtils.isNotEmpty(eqList)) {

            for (String param : eqList) {

                whereStrList.add("{\"" + SqlUtil.getFieldName(param) + "\":?}");
            }
        }


        if (CollectionUtils.isNotEmpty(ltList)) {

            for (String param : ltList) {

                whereStrList.add("{\"" + SqlUtil.getFieldName(param) + "\":{\"$lt\":?}}");
            }
        }

        return whereStrList;
    }

    /**
     * 打印sql语句
     * @param sql
     * @param paramList
     */
    public static void printSQL(String sql, List<Object[]> paramList) {
        // 使用 StringBuilder 构建日志信息
        StringBuilder stringBuilder = new StringBuilder();

        // 添加 SQL 语句
        stringBuilder.append("执行语句: ")
                .append(sql);

        // 遍历参数列表并格式化
        if (paramList != null && !paramList.isEmpty()) {
            stringBuilder.append("，参数列表: ");

            for (Object[] params : paramList) {
                stringBuilder.append("[")
                        .append(Arrays.stream(params)
                                .map(SqlUtil::formatParameter)
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
    private static String formatParameter(Object param) {
        if (param instanceof Date) {
            return DateFormatUtils.format((Date) param, "yyyy-MM-dd HH:mm:ss.SSS");
        }
        return Objects.toString(param);
    }

}
