# admin

后台管理系统

前端地址: https://github.com/zykzhangyukang/admin-ui

### 1. 启动参数

```
-Dspring.profiles.active=dev
-Dlog.file=D:/java/logs/admin
-Ddomain=admin
-Dsecret.key=XXX[配置文件秘钥]
-Xms256m
-Xmx512m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=D:/java/logs/oom
```

### 2. Linux部署参数

```shell script
nohup java -Dserver.port=8989 -Ddomain=admin -Dspring.profiles.active=pro -Dsecret=xxxx -Dlog.file=/root/app/logs  -jar admin-web-1.0.0-DEV-SNAPSHOT.jar &
```