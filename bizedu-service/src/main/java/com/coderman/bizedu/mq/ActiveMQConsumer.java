package com.coderman.bizedu.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.Escape;

import javax.jms.*;

/**
 * @ProjectName: springbootActiveMQ
 * @Package: cn.**.test
 * @Author: huat
 * @Date: 2020/1/3 8:47
 * @Version: 1.0
 */
@Slf4j
public class ActiveMQConsumer {
    //url路径
    private static final String ACTRIVE_URL="failover:(nio://localhost:61616)";
    //队列名称
    private static final String QUEUE_NAME="SYNC_QUEUE_DEV";


    public static void main(String[] args) {

        //1、创建连接工厂
        //如果账号密码没有修改的话，账号密码默认均为admin
        ActiveMQConnectionFactory connectionFactory=new ActiveMQConnectionFactory(ACTRIVE_URL);

        // RedeliveryPolicy redeliveryPolicy = connectionFactory.getRedeliveryPolicy();
        // 是否每次尝试重新发送失败后，增长这个等待时间
        // redeliveryPolicy.setUseExponentialBackOff(true);
        // 最大重试次数
        // redeliveryPolicy.setMaximumRedeliveries(8);
        // 重发时间间隔，默认1秒
        // redeliveryPolicy.setInitialRedeliveryDelay(1000);
        // 第一次失败后重新发送之前等待500毫秒，第二次失败再等待 500*2 毫秒，这里的2是value
        // redeliveryPolicy.setBackOffMultiplier(2);
        // 最大传送延迟
        // redeliveryPolicy.setRedeliveryDelay(1000L);

        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(connectionFactory);
        pooledConnectionFactory.setMaxConnections(5);

        //如果账号密码修改的话
        //第一个参数为账号，第二个为密码，第三个为请求的url
        //ActiveMQConnectionFactory activeMQConnectionFactory1=new ActiveMQConnectionFactory("admin","admin",ACTRIVE_URL);
        try {
            //2、通过连接工厂获取连接
            Connection connection = pooledConnectionFactory.createConnection();
            connection.start();
            //3、创建session会话
            //里面会有两个参数，第一个为事物，第二个是签收
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            //4、这里接受的queue的名称要和发送者的一致
            Queue queue = session.createQueue(QUEUE_NAME);
            //5、创建消费者
            MessageConsumer consumer = session.createConsumer(queue);
            //6、通过监听的方式消费消息
            while(true){
                //MessageConsumer 调用的receive方法为同步调用，在消息到达之前一直阻塞线程
                //用什么格式发送，这里就用什么格式接受
                //receive等待消息，不限制时间
                TextMessage message=(TextMessage)consumer.receive();

                //receive带参数等待消息，限制时间，单位毫秒
                //TextMessage message=(TextMessage)consumer.receive(4000L);

                if(null != message){

                    int redeliveryCount = message.getIntProperty("JMSXDeliveryCount");
                    if(redeliveryCount > 8){

                        log.info("超过重试8次数自动ack,id:{}", message.getJMSMessageID());
                        message.acknowledge();
                    }else {

                        log.info("接受到消息:{} ,id:{},redeliveryCount:{}", message.getText(), message.getJMSMessageID(),redeliveryCount);

                        if(StringUtils.equals(message.getText() , "error")){

                            session.recover();


                        }else {

                            message.acknowledge();
                        }
                    }

                }else{
                    break;
                }
            }
            //7、闭资源
            consumer.close();
            session.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
