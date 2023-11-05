package com.coderman.bizedu.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQTest {

    //url路径
    private static final String ACTRIVE_URL="tcp://165.154.133.250:61616";
    //队列名称
    private static final String QUEUE_NAME="queue01";

    public static void main(String[] args) {
        //1、创建连接工厂
        //如果账号密码没有修改的话，账号密码默认均为admin
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTRIVE_URL);

        try {
            //2、通过连接工厂获取连接
            Connection connection = activeMQConnectionFactory.createConnection();
            connection.start();
            //3、创建session会话
            //里面会有两个参数，第一个为事物，第二个是签收
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //4、创建目的地（具体是队列还是主题）,这里是创建队列
            Queue queue=session.createQueue(QUEUE_NAME);
            //5、创建消息生产者，队列模式
            MessageProducer messageProducer = session.createProducer(queue);
            //6、通过messageProducer生产三条消息发送到MQ消息队列中
            for (int i=0;i<3000;i++){

                //7、创建消息
                TextMessage textMessage = session.createTextMessage("msg----->" + i);//创建一个文本消息

                //8、通过messageProducer发送给mq
                messageProducer.send(textMessage);
                //9、数据非持久化
                messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
            }
            messageProducer.close();
            session.commit();
            session.close();
            connection.close();
            System.out.println("消息发送成功");
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}