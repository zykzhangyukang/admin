# Admin Backend Management System

这是一个后台管理系统的开源项目。该系统旨在帮助你管理和配置应用。前端代码可以通过 [admin-ui](https://github.com/zykzhangyukang/admin-ui) 找到。

## 技术栈

- **后端**：Java 1.8，Spring Boot
- **前端**：Vue 2，Element UI
- **构建工具**：Maven 3.6.3
- **Node.js**：v16.17.0
- **搜索引擎**：Elasticsearch 7.6.2
- **数据库**：MySQL 5.7
- **消息队列**：RocketMQ 4.7.0


## 部署和启动

### 1. 启动参数

为了启动该项目，请使用以下 Java 启动参数：

```bash
-Dspring.profiles.active=dev
-Dlog.file=D:/java/logs/admin      # 配置日志路径
-Ddomain=admin                     # 设置应用域名
-Dsecret.key=XXX                   # 配置文件秘钥
-Xms256m                           # 设置 JVM 初始内存
-Xmx512m                           # 设置 JVM 最大内存
-XX:+HeapDumpOnOutOfMemoryError    # 启用 OOM 时生成堆转储
-XX:HeapDumpPath=D:/java/logs/oom  # 堆转储文件路径
```

### 2. 部署

```bash
nohup java \
-Dserver.port=8989 \
-Ddomain=admin \
-Dspring.profiles.active=pro \
-Dsecret.key=xxxx \
-Dlog.file=/root/app/logs \
-jar admin-web-1.0.0-DEV-SNAPSHOT.jar \
> /root/app/logs/admin.log 2>&1 &
```

## 贡献
欢迎任何形式的贡献！如果你发现 bug、希望新增功能，或者有其他建议，请创建一个 issue 或者直接提交 pull request。
