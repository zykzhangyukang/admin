package com.coderman.admin.sync.db;

import com.coderman.admin.sync.config.SyncJdbcConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangyukang
 */
public class DbConfigBuilder {

    private static SyncJdbcConfig syncJdbcConfig;


    public static List<AbstractDbConfig> build(SyncJdbcConfig syncJdbcConfig) {

        DbConfigBuilder.syncJdbcConfig = syncJdbcConfig;

        List<SyncJdbcConfig.DbConfig> dbList = syncJdbcConfig.getDbList();

        if(CollectionUtils.isEmpty(dbList)){

            return Collections.emptyList();
        }

        List<AbstractDbConfig> configList = new ArrayList<>(dbList.size());


        for (SyncJdbcConfig.DbConfig datasource : dbList) {

            String url = datasource.getUrl();
            String type = datasource.getType();

            if (StringUtils.startsWith(url, "jdbc:mysql")) {

                configList.add(getMySQLConfig(datasource));

            } else if (StringUtils.startsWith(url, "jdbc:jtds:sqlserver:")) {

                configList.add(getMSSQLDbConfig(datasource,MSSQLConfig.DRIVER_JTDS));

            } else if (StringUtils.startsWith(url, "jdbc:sqlserver:")) {


                configList.add(getMSSQLDbConfig(datasource,MSSQLConfig.DRIVER_MICROSOFT));


            } else if (StringUtils.startsWith(url, "oracle")) {

                configList.add(getOracleDbConfig(datasource));

            } else if (StringUtils.equalsIgnoreCase(type, "mongo")) {

                configList.add(getMongoConfig(datasource));

            }

        }

        return configList;
    }

    private static MongoConfig getMongoConfig(SyncJdbcConfig.DbConfig dbConfig) {

        // mongo 配置
        String dbname = dbConfig.getDbname();
        String url = dbConfig.getUrl();
        String username = dbConfig.getUsername();
        String password = dbConfig.getPassword();
        Integer connectionsPerHost = dbConfig.getConnectionsPerHost();
        Integer maxWaitTime = dbConfig.getMaxWaitTime();
        Integer connectTimeout = dbConfig.getConnectTimeout();
        Integer serverSelectionTimeout = dbConfig.getServerSelectionTimeout();
        String socketKeepAlive = dbConfig.getSocketKeepAlive();
        Integer socketTimeout = dbConfig.getSocketTimeout();
        Integer threadsAllowedToBlockForConnectionMultiplier = dbConfig.getThreadsAllowedToBlockForConnectionMultiplier();


        // 跳过sync的配置
        if(StringUtils.equalsIgnoreCase("sync",dbname)){
            return null;
        }

        MongoConfig mongoConfig = new MongoConfig();
        mongoConfig.setBeanId(dbname);
        mongoConfig.setUrl(url);
        mongoConfig.setUserName(username);
        mongoConfig.setPassword(password);
        mongoConfig.setDb(dbname);
        mongoConfig.setConnectionsPerHost(connectionsPerHost);
        mongoConfig.setConnectTimeout(connectTimeout);
        mongoConfig.setMaxWaitTime(maxWaitTime);
        mongoConfig.setServerSelectionTimeout(serverSelectionTimeout);
        mongoConfig.setSocketKeepAlive(socketKeepAlive);
        mongoConfig.setSocketTimeout(socketTimeout);
        mongoConfig.setThreadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);

        return mongoConfig;
    }


    private static AbstractDbConfig getOracleDbConfig(SyncJdbcConfig.DbConfig dbConfig) {

        OracleConfig oracleConfig = new OracleConfig();

        oracleConfig.setBeanId(dbConfig.getDbname());
        oracleConfig.setDriverClassName(OracleConfig.DRIVER_ORACLE);


        // 读取配置
        String maxIdle = Optional.ofNullable(dbConfig.getMaxIdle()).orElse(syncJdbcConfig.getCommonMaxIdle());
        String maxActive = Optional.ofNullable(dbConfig.getMaxActive()).orElse(syncJdbcConfig.getCommonMaxActive());
        String minIdle = Optional.ofNullable(dbConfig.getMinIdle()).orElse(syncJdbcConfig.getCommonMinIdle());
        String maxWait = Optional.ofNullable(dbConfig.getMaxWait()).orElse(syncJdbcConfig.getCommonMaxWait());
        String username = Optional.ofNullable(dbConfig.getUsername()).orElse(syncJdbcConfig.getMysqlUsername());
        String timeout = Optional.ofNullable(dbConfig.getTimeout()).orElse(syncJdbcConfig.getCommonTimeout());
        String password = Optional.ofNullable(dbConfig.getPassword()).orElse(syncJdbcConfig.getMysqlPassword());


        oracleConfig.setMaxIdle(maxIdle);
        oracleConfig.setMaxActive(maxActive);
        oracleConfig.setMaxWait(maxWait);
        oracleConfig.setMinIdle(minIdle);
        oracleConfig.setTransTimeout(timeout);

        oracleConfig.setUrl(dbConfig.getUrl());
        oracleConfig.setUserName(username);
        oracleConfig.setPassword(password);

        String timeBetweenEvictionRunsMillis = Optional.ofNullable(dbConfig.getTimeBetweenEvictionRunsMillis()).orElse(syncJdbcConfig.getMysqlTimeBetweenEvictionRunsMillis());
        String minEvictableIdleTimeMillis = Optional.ofNullable(dbConfig.getMinEvictableIdleTimeMillis()).orElse(syncJdbcConfig.getMysqlMinEvictableIdleTimeMillis());
        String testOnBorrow = Optional.ofNullable(dbConfig.getTestOnBorrow()).orElse(syncJdbcConfig.getMysqlTestOnBorrow());
        String testOnReturn = Optional.ofNullable(dbConfig.getTestOnReturn()).orElse(syncJdbcConfig.getMysqlTestOnReturn());
        String testWhileIdle = Optional.ofNullable(dbConfig.getTestWhileIdle()).orElse(syncJdbcConfig.getMysqlTestWhileIdle());


        oracleConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        oracleConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        oracleConfig.setTestOnBorrow(testOnBorrow);
        oracleConfig.setTestOnReturn(testOnReturn);
        oracleConfig.setTestWhileIdle(testWhileIdle);

        return oracleConfig;
    }

    private static AbstractDbConfig getMSSQLDbConfig(SyncJdbcConfig.DbConfig dbConfig, String driverClassName) {

        MSSQLConfig mssqlConfig = new MSSQLConfig();

        mssqlConfig.setDriverClassName(driverClassName);
        mssqlConfig.setBeanId(dbConfig.getDbname());

        // 读取配置
        String maxActive = Optional.ofNullable(dbConfig.getMaxActive()).orElse(syncJdbcConfig.getCommonMaxActive());
        String maxIdle = Optional.ofNullable(dbConfig.getMaxIdle()).orElse(syncJdbcConfig.getCommonMaxIdle());
        String maxWait = Optional.ofNullable(dbConfig.getMaxWait()).orElse(syncJdbcConfig.getCommonMaxWait());
        String username = Optional.ofNullable(dbConfig.getUsername()).orElse(syncJdbcConfig.getMysqlUsername());
        String minIdle = Optional.ofNullable(dbConfig.getMinIdle()).orElse(syncJdbcConfig.getCommonMinIdle());
        String timeout = Optional.ofNullable(dbConfig.getTimeout()).orElse(syncJdbcConfig.getCommonTimeout());
        String password = Optional.ofNullable(dbConfig.getPassword()).orElse(syncJdbcConfig.getMysqlPassword());

        String timeBetweenEvictionRunsMillis = Optional.ofNullable(dbConfig.getTimeBetweenEvictionRunsMillis()).orElse(syncJdbcConfig.getMysqlTimeBetweenEvictionRunsMillis());
        String minEvictableIdleTimeMillis = Optional.ofNullable(dbConfig.getMinEvictableIdleTimeMillis()).orElse(syncJdbcConfig.getMysqlMinEvictableIdleTimeMillis());
        String testOnReturn = Optional.ofNullable(dbConfig.getTestOnReturn()).orElse(syncJdbcConfig.getMysqlTestOnReturn());
        String testOnBorrow = Optional.ofNullable(dbConfig.getTestOnBorrow()).orElse(syncJdbcConfig.getMysqlTestOnBorrow());
        String testWhileIdle = Optional.ofNullable(dbConfig.getTestWhileIdle()).orElse(syncJdbcConfig.getMysqlTestWhileIdle());


        mssqlConfig.setMaxActive(maxActive);
        mssqlConfig.setMaxIdle(maxIdle);
        mssqlConfig.setMinIdle(minIdle);
        mssqlConfig.setMaxWait(maxWait);
        mssqlConfig.setTransTimeout(timeout);

        mssqlConfig.setUrl(dbConfig.getUrl());
        mssqlConfig.setUserName(username);
        mssqlConfig.setPassword(password);

        mssqlConfig.setLogAbandoned(dbConfig.getLogAbandoned());
        mssqlConfig.setRemoveAbandoned(dbConfig.getRemoveAbandoned());
        mssqlConfig.setRemoveAbandonedTimeout(dbConfig.getRemoveAbandonedTimeout());

        mssqlConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        mssqlConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        mssqlConfig.setTestOnBorrow(testOnBorrow);
        mssqlConfig.setTestOnReturn(testOnReturn);
        mssqlConfig.setTestWhileIdle(testWhileIdle);

        return mssqlConfig;

    }

    private static AbstractDbConfig getMySQLConfig(SyncJdbcConfig.DbConfig dbConfig) {

        MySQLConfig mySQLConfig = new MySQLConfig();

        String key = dbConfig.getDbname();

        // 注册bean key
        mySQLConfig.setBeanId(key);

        // 读取配置
        String maxActive = Optional.ofNullable(dbConfig.getMaxActive()).orElse(syncJdbcConfig.getCommonMaxActive());
        String maxIdle = Optional.ofNullable(dbConfig.getMaxIdle()).orElse(syncJdbcConfig.getCommonMaxIdle());
        String maxWait = Optional.ofNullable(dbConfig.getMaxWait()).orElse(syncJdbcConfig.getCommonMaxWait());
        String minIdle = Optional.ofNullable(dbConfig.getMinIdle()).orElse(syncJdbcConfig.getCommonMinIdle());
        String timeout = Optional.ofNullable(dbConfig.getTimeout()).orElse(syncJdbcConfig.getCommonTimeout());
        String username = Optional.ofNullable(dbConfig.getUsername()).orElse(syncJdbcConfig.getMysqlUsername());
        String password = Optional.ofNullable(dbConfig.getPassword()).orElse(syncJdbcConfig.getMysqlPassword());
        String initialSize = Optional.ofNullable(dbConfig.getInitialSize()).orElse(syncJdbcConfig.getMysqlInitialSize());

        String timeBetweenEvictionRunsMillis = Optional.ofNullable(dbConfig.getTimeBetweenEvictionRunsMillis()).orElse(syncJdbcConfig.getMysqlTimeBetweenEvictionRunsMillis());

        String minEvictableIdleTimeMillis = Optional.ofNullable(dbConfig.getMinEvictableIdleTimeMillis()).orElse(syncJdbcConfig.getMysqlMinEvictableIdleTimeMillis());
        String testOnBorrow = Optional.ofNullable(dbConfig.getTestOnBorrow()).orElse(syncJdbcConfig.getMysqlTestOnBorrow());
        String testWhileIdle = Optional.ofNullable(dbConfig.getTestWhileIdle()).orElse(syncJdbcConfig.getMysqlTestWhileIdle());
        String testOnReturn = Optional.ofNullable(dbConfig.getTestOnReturn()).orElse(syncJdbcConfig.getMysqlTestOnReturn());


        mySQLConfig.setMaxActive(maxActive);
        mySQLConfig.setMaxIdle(maxIdle);
        mySQLConfig.setMaxWait(maxWait);
        mySQLConfig.setMinIdle(minIdle);
        mySQLConfig.setTransTimeout(timeout);

        mySQLConfig.setUrl(dbConfig.getUrl());
        mySQLConfig.setUserName(username);
        mySQLConfig.setPassword(password);

        mySQLConfig.setInitialSize(initialSize);
        mySQLConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        mySQLConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        mySQLConfig.setTestOnBorrow(testOnBorrow);
        mySQLConfig.setTestOnReturn(testOnReturn);
        mySQLConfig.setTestWhileIdle(testWhileIdle);

        return mySQLConfig;
    }
}
