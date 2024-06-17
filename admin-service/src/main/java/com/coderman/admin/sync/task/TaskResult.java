package com.coderman.admin.sync.task;

import com.coderman.admin.sync.sql.meta.SqlMeta;
import lombok.Data;

import java.util.List;

@Data
public class TaskResult {


    private String code;


    private List<SqlMeta> resultList;


    private String errorMsg;


    private  boolean retry;
}
