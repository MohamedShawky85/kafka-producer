package com.kafka.producer.controller;


import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class KafkaController {
	
	private static final Logger log = Logger.getLogger(KafkaController.class.getName());


    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/produce")
    public String produceMessage(@RequestBody String message) {
    	log.log(Level.SEVERE, "Recieved message: {0} ", message);
        kafkaTemplate.sendDefault(message);
        return "Message sent to Kafka topic successfully!";
    }
}
