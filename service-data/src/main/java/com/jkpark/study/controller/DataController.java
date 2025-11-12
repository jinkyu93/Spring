package com.jkpark.study.controller;

import com.jkpark.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {

    @GetMapping("/message")
    public Message getMessage() {
        Message message = new Message();
        String text = "Hello from service-data";
        message.setMessage(text);
        return message;
    }
}
