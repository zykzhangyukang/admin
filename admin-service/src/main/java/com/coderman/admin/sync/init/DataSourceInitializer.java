package com.coderman.admin.sync.init;

import com.coderman.admin.sync.config.SyncDBConfig;
import com.coderman.admin.sync.context.SyncContext;
import com.coderman.admin.sync.db.*;
import com.coderman.admin.sync.executor.AbstractExecutor;
import com.coderman.admin.sync.util.SyncBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyukang
 */
@Lazy(value = false)
@Component
@Slf4j
public class DataSourceInitializer {

    @Resource
    private SyncDBConfig config;

    /**
     * 初始化数据源
     */
    public void init() {


        List<AbstractDbConfig> dbConfigList = DbConfigBuilder.build(config);

        if (CollectionUtils.isEmpty(dbConfigList)) {
            return;
        }

        for (AbstractDbConfig dbConfig : dbConfigList) {

            if (dbConfig instanceof MySQLConfig) {

                SyncBeanUtil.registerMySQLDataSource((MySQLConfig) dbConfig);

            } else if (dbConfig instanceof MSSQLConfig) {

                SyncBeanUtil.registerMSSQLDataSource((MSSQLConfig) dbConfig);

            } else if (dbConfig instanceof OracleConfig) {

                SyncBeanUtil.registerOracleDataSource((OracleConfig) dbConfig);

            } else if (dbConfig instanceof MongoConfig) {

                SyncBeanUtil.registerMongoDataSource((MongoConfig) dbConfig);
            }
        }

        // 初始化数据库执行器
        for (Map.Entry<String, String> entry : SyncContext.getContext().getDbTypeMap().entrySet()) {

            String dbName = entry.getKey();
            try {

                AbstractExecutor abstractExecutor = AbstractExecutor.build(dbName);

                log.info("数据库{} 连接初始化完成:{}", dbName, abstractExecutor);
            } catch (Exception e) {
                log.error("数据库{} 连接初始化失败:{}", dbName, e.getMessage());
            }
        }
    }
}
