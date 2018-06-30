package com.wangchao.server.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqReceiverTest {

    @Test
    public void process() {

    }

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void send(){
        amqpTemplate.convertAndSend("myQueue3","hello wang");
    }
}