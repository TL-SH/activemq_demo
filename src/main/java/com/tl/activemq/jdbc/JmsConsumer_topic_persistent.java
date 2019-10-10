package com.tl.activemq.jdbc;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author tanglei
 */
public class JmsConsumer_topic_persistent {
    public static final String ACTIVEMQ_URL = "tcp://192.168.43.166:61616";
    public static final String TOPIC_NAME = "jdbc_topic_persistent";
    public static void main(String[] args) throws Exception{
        System.out.println("***z3");

        //1.创建连接工场,按照给定的url地址,采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场,获得连接的connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("z3");
        //3.创建session会话
        //两个参数,第一个参数叫事务/第二个参数叫签收
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地(具体的队列还是主题topic)
        Topic topic = session.createTopic(TOPIC_NAME);
        //发布订阅
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "leishuai....");
        connection.start();

        Message message = topicSubscriber.receive();
        while (null!=message){
            TextMessage textMessage = (TextMessage)message;
            System.out.println("***收到的持久化topic:"+textMessage.getText());
            message = topicSubscriber.receive(1000l);
        }

        session.close();
        connection.close();

    }

}
