package com.tl.activemq.persistent;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author tanglei
 */
public class JmsConsumer_persistent {
    public static final String ACTIVEMQ_URL = "tcp://121.199.40.167:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("***我是1号消费者");

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
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        messageConsumer.setMessageListener((message)-> {
                if(null!=message && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        System.out.println("****消费者接收到textMessage消息:"+textMessage.getText());

                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();

    }
}