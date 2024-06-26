package com.coderman.admin.sync.plan.meta;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.coderman.admin.sync.context.SyncContext;
import com.coderman.api.model.BaseModel;
import com.coderman.admin.sync.constant.SyncConstant;
import com.coderman.admin.sync.exception.ErrorCodeEnum;
import com.coderman.admin.sync.exception.SyncException;
import com.coderman.admin.sync.task.SyncConvert;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MQ消息元数据
 *
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MsgMeta extends BaseModel {

    /**
     * 同步计划编号
     */
    private String planCode;

    /**
     * 消息创建时间
     */
    private Date createDate;


    /**
     * 消息类型
     */
    private String msgType;


    /**
     * 消息来源项目
     */
    private String srcProject;

    /**
     * 消息uuid
     */
    private String msgId;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 要执行的任务
     */
    private List<MsgTableMeta> tableMetaList;


    /**
     * 构建消息元数据
     *
     * @param msg 消息内容
     * @return 消息元数据
     */
    public static MsgMeta build(String msg) throws SyncException{

        MsgMeta msgMeta = new MsgMeta();

        try {

            JSONObject msgJson = (JSONObject) JSONObject.parse(msg, Feature.OrderedField);

            String planCode = msgJson.getString("plan");
            PlanMeta planMeta = SyncContext.getContext().getPlanMeta(planCode);

            List<MsgTableMeta> tableMetaList = new ArrayList<>();
            msgMeta.setMsg(msg);
            msgMeta.setPlanCode(planCode);

            // 消息类型
            if (msgJson.containsKey("type")) {

                msgMeta.setMsgType(msgJson.getString("type"));
            } else {

                msgMeta.setMsgType(SyncConstant.MSG_TYPE_SYNC);
            }

            // MQ消息id
            if (msgJson.containsKey("msgId")) {

                msgMeta.setMsgId(msgJson.getString("msgId"));
            }

            // 消息来源
            if (msgJson.containsKey("src")) {

                msgMeta.setSrcProject(msgJson.getString("src"));
            }

            // 创建时间
            if (msgJson.containsKey("createTime")) {

                msgMeta.setCreateDate(msgJson.getDate("createTime"));
            }

            JSONArray tablesJson = msgJson.getJSONArray("tables");

            for (int i = 0; i < tablesJson.size(); i++) {


                JSONObject json = tablesJson.getJSONObject(i);

                List<Object> uniqueList = new ArrayList<>();
                MsgTableMeta tableMeta = new MsgTableMeta();
                tableMeta.setCode(json.getString("code"));
                tableMeta.setUniqueList(uniqueList);


                // 结果影响行数
                if (json.containsKey("affectNum")) {

                    tableMeta.setAffectNum(Integer.valueOf(json.getString("affectNum")));
                }

                // 获取计划中的指定的unique数据类型
                String originUnitType = null;

                try {

                    if (planMeta != null) {
                        originUnitType = planMeta.getUniqueTypeByCode(json.getString("code"));
                    }

                } catch (Exception ignored) {

                }

                for (int j = 0; j < ((JSONArray) json.get("unique")).size(); j++) {

                    String unique = ((JSONArray) json.get("unique")).get(j).toString();

                    if (!SyncConvert.DATA_TYPE_STRING.equals(tableMeta.getUniqueType()) && StringUtils.isNumeric(unique)) {
                        tableMeta.setUniqueType(SyncConvert.DATA_TYPE_INT);
                    } else {
                        tableMeta.setUniqueType(SyncConvert.DATA_TYPE_STRING);
                    }

                    if (originUnitType != null) {

                        tableMeta.setUniqueType(originUnitType);
                    }

                    if (!uniqueList.contains(unique)) {
                        uniqueList.add(unique);
                    }
                }

                tableMetaList.add(tableMeta);
            }

            msgMeta.setTableMetaList(tableMetaList);

        } catch (Exception e) {

            throw new SyncException(ErrorCodeEnum.PARSE_MSG_ERROR, "解析同步消息错误, msg:" + msg);
        }

        return msgMeta;
    }
}
