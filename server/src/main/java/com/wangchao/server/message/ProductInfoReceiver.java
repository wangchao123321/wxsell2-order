package com.wangchao.server.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wangchao.common.ProductInfoOutput;
import com.wangchao.server.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductInfoReceiver {

    private static final String PRODUCT_STOCK_TEMPLATE="product_stock_%s";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        List<ProductInfoOutput> productInfoOutputs= (List<ProductInfoOutput>) JsonUtil.fromJson(message, new TypeReference<ProductInfoOutput>(){});
        log.info("从队列{}接受到消息: {}","productInfo",productInfoOutputs);

        // 存储到Redis中
        for (ProductInfoOutput productInfoOutput : productInfoOutputs) {
            redisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,productInfoOutput.getProductId()),String.valueOf(productInfoOutput.getProductStock()));
        }
    }



}
