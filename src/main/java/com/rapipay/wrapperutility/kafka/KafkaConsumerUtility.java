package com.rapipay.wrapperutility.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumerUtility {
    private static final Logger log= LogManager.getLogger(KafkaConsumerUtility.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public String consume(String message){
        log.info("Message Received : {}",message);
        return message;
    }
}
