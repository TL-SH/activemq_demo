package com.tl.activemq.embed;

import org.apache.activemq.broker.BrokerService;

/**
 * @author tanglei
 */
public class EmbedBroker {

    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }

}
