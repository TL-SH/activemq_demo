package com.tl.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author tanglei
 */
public class JmsProducer {
    public static final String ACTIVEMQ_URL = "tcp://192.168.43.166:61616";
    public static final String QUEUE_NAME = "queue";


    public static void main(String[] args) throws JMSException {
        //1.创建连接工场,按照给定的url地址,采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场,获得连接的connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建session会话
        //两个参数,第一个参数叫事务/第二个参数叫签收
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地(具体的队列还是主题topic)
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //6.通过使用messageProducer生产3条消息发送到MQ的队列里面
        for (int i = 1; i <=6 ; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("msgListener---" + i);
            //textMessage.setStringProperty("co1","vip");
            //8.通过messageProducer发送给mq
            messageProducer.send(textMessage);

            /*MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("k1","mapMessage -- v"+i);
            messageProducer.send(mapMessage);*/

        }
        //9.关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("******消息发布到MQ完成");

    }

}
