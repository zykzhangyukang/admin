# bizedu
企业在线学习和培训平台

#### 1. 命令行启动参数
```text
-Dserver.port=8989
-Ddomain=bizedu
-Dspring.profiles.active=dev
-Dlog.file=D:\log\springboot\bizedu
-Dspring.main.allow-bean-definition-overriding=true
-Dsecret.key=xxxx[配置文件加密key]
```

#### 2. 项目部署参数
```shell script
nohup java -Dserver.port=8989 -Ddomain=bizedu -Dspring.profiles.active=pro -Dsecret=xxxx -Dlog.file=/root/app/logs -Dspring.main.allow-bean-definition-overriding=true  -jar bizedu-web-1.0.0-DEV-SNAPSHOT.jar &
```


#### 3. 软件下载

https://webradio.hinekure.net/ffmpeg/ffmpeg.zeranoe.com/builds/win64/static/ [windows]