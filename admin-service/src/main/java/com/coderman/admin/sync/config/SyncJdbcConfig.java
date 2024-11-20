package com.coderman.admin.sync.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 配置类：用于同步系统的JDBC及Mongo数据库配置管理
 * 提供多数据库的连接池配置，支持MySQL、SQLServer、MongoDB等。
 * 每种数据库类型对应特定的参数配置。
 *
 * @author zhangyukang
 */
@Configuration
@ConfigurationProperties(prefix = "sync.jdbc")
@Data
public class SyncJdbcConfig {

    // 通用连接池配置
    private String commonMaxIdle; // 最大空闲连接数
    private String commonMaxWait; // 获取连接的最大等待时间（单位：毫秒）
    private String commonMinIdle; // 最小空闲连接数
    private String commonMaxActive; // 最大活跃连接数
    private String commonTimeout; // 连接超时时间（单位：毫秒）

    // MySQL数据库的基本配置
    private String mysqlUsername; // MySQL数据库用户名
    private String mysqlPassword; // MySQL数据库密码
    private String mysqlInitialSize; // MySQL连接池的初始化连接数

    // MySQL连接池高级配置
    private String mysqlMinEvictableIdleTimeMillis; // 最小空闲连接的存活时间（单位：毫秒）
    private String mysqlTimeBetweenEvictionRunsMillis; // 空闲连接检查的时间间隔（单位：毫秒）
    private String mysqlTestOnBorrow; // 是否在获取连接时验证其有效性
    private String mysqlTestOnReturn; // 是否在归还连接时验证其有效性
    private String mysqlTestWhileIdle; // 是否在空闲时验证连接的有效性

    /**
     * 多数据库的配置列表：支持多个数据库类型的配置
     */
    @Data
    public static final class DbConfig {
        private String dbname; // 数据库名称
        private String url; // 数据库连接URL
        private String type; // 数据库类型（如：mysql、sqlserver、mongodb）
        private String username; // 数据库用户名
        private String password; // 数据库密码

        // 连接池通用配置
        private String maxIdle; // 最大空闲连接数
        private String maxWait; // 获取连接的最大等待时间（单位：毫秒）
        private String minIdle; // 最小空闲连接数
        private String maxActive; // 最大活跃连接数
        private String timeout; // 连接超时时间（单位：毫秒）

        // 高级连接池配置
        private String minEvictableIdleTimeMillis; // 最小空闲连接的存活时间（单位：毫秒）
        private String timeBetweenEvictionRunsMillis; // 空闲连接检查的时间间隔（单位：毫秒）
        private String testOnBorrow; // 是否在获取连接时验证其有效性
        private String testOnReturn; // 是否在归还连接时验证其有效性
        private String testWhileIdle; // 是否在空闲时验证连接的有效性

        /**
         * MySQL专用配置
         */
        private String initialSize; // MySQL连接池的初始化连接数

        /**
         * SQLServer专用配置
         */
        private String logAbandoned; // 是否记录长时间未关闭的连接日志
        private String removeAbandoned; // 是否自动移除长时间未关闭的连接
        private String removeAbandonedTimeout; // 长时间未关闭的连接的超时时间（单位：秒）

        /**
         * MongoDB专用配置
         */
        private String db; // MongoDB的数据库名称
        private Integer connectionsPerHost; // 每个主机的最大连接数
        private Integer connectTimeout; // MongoDB的连接超时时间（单位：毫秒）
        private Integer maxWaitTime; // MongoDB连接池获取连接的最大等待时间（单位：毫秒）
        private Integer serverSelectionTimeout; // 选择服务器的超时时间（单位：毫秒）
        private String socketKeepAlive; // 是否启用Socket的KeepAlive选项
        private Integer socketTimeout; // Socket的超时时间（单位：毫秒）
        private Integer threadsAllowedToBlockForConnectionMultiplier; // 线程等待连接的倍数限制
    }

    // 数据库配置列表：支持多个数据库配置
    private List<DbConfig> dbList;
}
