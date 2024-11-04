package com.coderman.admin.vo.common;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Map;

@Data
public class FundBeanVO {
    @JSONField(name = "fundcode")
    private String fundCode;
    @JSONField(name = "name")
    private String fundName;
    private String jzrq;//净值日期
    private String dwjz;//当日净值
    private String gsz; //估算净值
    private String gszzl;//估算涨跌百分比 即-0.42%
    private String gztime;//gztime估值时间

    private String costPrise;//持仓成本价
    private String bonds;//持有份额
    private String incomePercent;//收益率
    private String income;//收益
    private String todayIncome; // 当前收益

    public FundBeanVO() {
    }


    public static void loadFund(FundBeanVO fund, Map<String, String[]> codeMap) {
        String code = fund.getFundCode();
        if (codeMap.containsKey(code)) {
            String[] codeStr = codeMap.get(code);
            if (codeStr.length > 2) {
                fund.setCostPrise(codeStr[1]);
                fund.setBonds(codeStr[2]);
            }
        }
    }

    @Override
    public String toString() {
        return "FundBean{" +
                "fundCode='" + fundCode + '\'' +
                ", fundName='" + fundName + '\'' +
                ", jzrq='" + jzrq + '\'' +
                ", dwjz='" + dwjz + '\'' +
                ", gsz='" + gsz + '\'' +
                ", gszzl='" + gszzl + '\'' +
                ", gztime='" + gztime + '\'' +
                ", costPrise='" + costPrise + '\'' +
                ", bonds='" + bonds + '\'' +
                ", incomePercent='" + incomePercent + '\'' +
                ", income='" + income + '\'' +
                ", todayIncome='" + todayIncome + '\'' +
                '}';
    }
}