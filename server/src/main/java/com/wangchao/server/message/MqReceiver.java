package com.wangchao.server.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqReceiver {

//    @RabbitListener(queues = "myQueue")
//    @RabbitListener(queuesToDeclare = @Queue("myQueue2"))
    @RabbitListener(bindings = @QueueBinding(value = @Queue("myQueue3"),exchange = @Exchange("myExchange")))
    public void process(String message){
        log.info("MqReceiver: {}",message);
    }

}
