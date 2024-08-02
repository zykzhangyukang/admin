# admin
后台管理系统

#### 1. 命令行启动参数
```text
-Dserver.port=8989
-Ddomain=admin
-Dspring.profiles.active=dev
-Dlog.file=D:\log\springboot\admin
-Dspring.main.allow-bean-definition-overriding=true
-Dsecret.key=xxxx[配置文件加密key]
```

#### 2. 项目部署参数
```shell script
nohup java -Dserver.port=8989 -Ddomain=admin -Dspring.profiles.active=pro -Dsecret=xxxx -Dlog.file=/root/app/logs  -jar admin-web-1.0.0-DEV-SNAPSHOT.jar &
```
