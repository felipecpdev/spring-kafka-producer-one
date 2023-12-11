package com.felipecpdev.fcpdevgateway.controllers;

import com.felipecpdev.fcpdevgateway.models.TransactionMessage;
import com.felipecpdev.fcpdevgateway.services.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event")
@Slf4j
public class EventController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping
    public ResponseEntity<String> event(@RequestBody TransactionMessage transactionMessage) {
        UUID uuid = UUID.randomUUID();
        kafkaProducerService.send("transaction-topic", uuid, transactionMessage);
        return ResponseEntity.ok("Sent");
    }
}
