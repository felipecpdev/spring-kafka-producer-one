package com.felipecpdev.fcpdevgateway.services;

import com.felipecpdev.fcpdevgateway.models.TransactionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class KafkaProducerService {

    @Autowired
    KafkaTemplate<UUID, TransactionMessage> kafkaTemplate;

    public void send(String topicName, UUID key, TransactionMessage transactionMessage) {
        var future = kafkaTemplate.send(topicName, key, transactionMessage);
        //async
        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                log.error(exception.getMessage());
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
            }
            log.info("the id is:" + transactionMessage
                    .getTransactionId() + ", transaction status to Kafka topic:"
                    + transactionMessage.getStatus());
        });
    }

}
