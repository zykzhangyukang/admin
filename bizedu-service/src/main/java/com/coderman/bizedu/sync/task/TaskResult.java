package com.coderman.bizedu.sync.task;

import com.coderman.bizedu.sync.sql.meta.SqlMeta;
import lombok.Data;

import java.util.List;

@Data
public class TaskResult {


    private String code;


    private List<SqlMeta> resultList;


    private String errorMsg;


    private  boolean retry;
}
