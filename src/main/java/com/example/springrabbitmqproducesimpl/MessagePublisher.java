package com.example.springrabbitmqproducesimpl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class MessagePublisher {

    @Autowired
    private RabbitTemplate template;

    @PostMapping("/sendFile")
    public String sendFile() throws IOException {
        File file = new File("C:\\CanevasSource.xlsx");
        byte[] bytesFromFile = Files.readAllBytes(file.toPath());
        CustomFile customFile = new CustomFile(bytesFromFile);
        template.convertAndSend(
                MQConfig.EXCHANGE_EXCEL,
                MQConfig.ROUTING_KEY_EXCEL,
                customFile);
        return "File publisher Excel";
    }
}
