package com.tl.activemq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

/**
 * @author tanglei
 * @date 2019/10/5  1:19
 */
@Service
public class SpringMQ_Produce {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        SpringMQ_Produce produce = applicationContext.getBean("springMQ_Produce", SpringMQ_Produce.class);

        produce.jmsTemplate.send((session)->{
            TextMessage textMessage = session.createTextMessage("Spring和ActiveMQ整和case11111111.....");
            return textMessage;
        });
        System.out.println(".....send task over...");
    }

}
