package com.tl.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author tanglei
 */
public class JmsConsumer_one {
    public static final String ACTIVEMQ_URL = "tcp://192.168.43.166:61616";
    public static final String QUEUE_NAME = "queue";

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

        /*
        同步阻塞方法(receive())
        订阅者或接受者调用MessageConsumer的receive()方法来接受消息,receive方法在能够接受收到消息之前(或超时之前)将一直阻塞
        while (true){
            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000l);
            if(null != textMessage){
                System.out.println("****消费者接收到消息:"+textMessage.getText());
            }else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();*/

        //使用监听器的方法
        /**
         * 异步非阻塞方式(监听器onMessage())
         * 订阅者或接受者通过MessaGeConsumer的setMessageListener(MessageListener listener)注册一个消息监听器
         * 当消息到大之后,系统自动调用监听器MessageListener的onMessage(Message message)方法
         */
        messageConsumer.setMessageListener((message)-> {

                if(null!=message && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        System.out.println("****消费者接收到textMessage消息:"+textMessage.getText());
                        //System.out.println("****消费者接收到textMessage属性:"+textMessage.getStringProperty("co1"));

                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

                /*if(null!=message && message instanceof MapMessage){
                    MapMessage mapMessage = (MapMessage)message;
                    try {
                        System.out.println("****消费者接收MapMessage到消息:"+mapMessage.getString("k1"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }*/

        });

        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();

    }
}