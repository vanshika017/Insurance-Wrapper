package com.rapipay.wrapperutility.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class KafkaProducerUtility {

    private static final Logger log = LogManager.getLogger(KafkaProducerUtility.class);
    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerUtility(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        log.info("Message sent : {}", message);
        kafkaTemplate.send("javaguides", message);
    }
}