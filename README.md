# bizedu
企业在线学习和培训平台

#### 1. 命令行启动参数
```text
-Dserver.port=8989
-Ddomain=bizedu
-Dspring.profiles.active=dev
-Dlog.file=D:\log\springboot\bizedu
-Dspring.main.allow-bean-definition-overriding=true
```

#### 2. 项目部署参数
nohup java -Dserver.port=8989 -Ddomain=bizedu -Dspring.profiles.active=pro -Dlog.file=/root/app/logs -Dspring.main.allow-bean-definition-overriding=true  -jar bizedu-web-1.0.0-DEV-SNAPSHOT.jar &