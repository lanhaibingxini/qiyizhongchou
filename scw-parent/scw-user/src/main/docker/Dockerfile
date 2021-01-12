# 基于哪个镜像
FROM java:8
# 拷贝文件到容器，也可以直接写成ADD xxxxx.jar /app.jar
ADD scw-user-*.jar  /app.jar

#配置环境变量，接受外部传递参数
#启动端口
ENV PORT=""
#应用名称，用来前端显示用，不是serviceId
ENV APPLICATION=""
#注册中心地址
ENV EUREKA=""
# 声明需要暴露的端口
EXPOSE 8888
# 配置容器启动后执行的命令
ENTRYPOINT ["sh","-c","java $PORT $APPLICATION $EUREKA  -jar /app.jar"]