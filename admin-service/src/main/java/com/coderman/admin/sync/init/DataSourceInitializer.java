package com.coderman.admin.sync.init;

import com.coderman.admin.sync.config.SyncJdbcConfig;
import com.coderman.admin.sync.db.*;
import com.coderman.admin.sync.util.SyncBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangyukang
 */
@Lazy(value = false)
@Component
@Slf4j
public class DataSourceInitializer {

    @Resource
    private SyncJdbcConfig jdbcConfig;

    /**
     * 初始化数据源
     */
    public void init() {


        List<AbstractDbConfig> dbConfigList = DbConfigBuilder.build(jdbcConfig);

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
    }
}
