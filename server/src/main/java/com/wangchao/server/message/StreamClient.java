//package com.wangchao.server.message;
//
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.SubscribableChannel;
//import org.springframework.stereotype.Component;
//
//@Component
//public interface StreamClient {
//
//    String INPUT="myMessage";
//
//    @Input(StreamClient.INPUT)
//    SubscribableChannel input();
//
//    @Output(StreamClient.INPUT)
//    MessageChannel output();
//}
