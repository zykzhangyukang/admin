# Admin

后台管理系统

前端地址: https://github.com/zykzhangyukang/admin-ui

> 该项目主要用于后台系统的手脚架，提供分布式环境下数据库同步的解决方案。（表与表之间的同步）

## 主要模块

1. **RBAC 权限模块**：
   - 用户
   - 角色
   - 功能
   - 资源

2. **同步系统模块**：
   - 嵌入式 ActiveMQ (无需手动安装MQ, 开箱即用, 当然也可以随便替换成其他MQ产品.)
   - 数据一致性 (基于本地消息表的最终一致性)
   - 补偿机制 (定时器补充, 手动补偿)
   - 同步记录追踪 (接入ES检索,方便快速定位问题. )


### 1. 启动步骤

#### 1.1 拉取基础依赖base-parent

```
git clone https://github.com/zykzhangyukang/base-parent.git
```

#### 1.2 修改配置文件 `application-dev.yml`
```yaml
server:
  port: 8989
spring:
  application:
    name: admin
  datasource:
    username: root # 改成自己的数据库账号
    password: DES@99D5518076FE8F32214D66F4B59766B7 # 数据库密码 (可以明文, 也可以使用DES@作为前置进行加密, 加密代码见 com.coderman.service.config.EncryptPropertyConfig)
    url: jdbc:mysql://127.0.0.1:3306/admin_dev?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8 # 数据库地址
    schema: classpath:db/schema.sql
    data: classpath:db/data.sql
    initialization-mode: never # 第一次初始化时候改成 always
  redis:
    host: 127.0.0.1
    port: 6379
  task:
    scheduling:
      pool:
        size: 4

sync:
  # es 配置
  elasticsearch:
    clusterName: docker-cluster
    hosts: 47.115.42.112:9200 # 改成自己的es地址
    scheme: http
    connectTimeOut: 5000
    socketTimeOut: 60000
    connectionRequestTimeOut: 500
    maxConnectNum: 100
    maxConnectNumPerRoute: 100
    username: elastic # es的账号, 没有账号可以不配置
    password:  DES@99D5518076FE8F32214D66F4B59766B7 # es的密码, 没有密码可以不配置
```

### 1.3 启动参数

```
-Dserver.port=8989 (端口)
-Ddomain=admin (项目名)
-Dspring.profiles.active=dev (环境)
-Dlog.file=D:\log\springboot\admin (日志文件地址)
-Dsecret.key=xxxx  (配置文件加密key,  加密代码见 com.coderman.service.config.EncryptPropertyConfig)
```

### 2. Linux部署参数

```shell script
nohup java -Dserver.port=8989 -Ddomain=admin -Dspring.profiles.active=pro -Dsecret=xxxx -Dlog.file=/root/app/logs  -jar admin-web-1.0.0-DEV-SNAPSHOT.jar &
```