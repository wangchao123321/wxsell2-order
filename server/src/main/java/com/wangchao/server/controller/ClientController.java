package com.wangchao.server.controller;

import com.netflix.discovery.converters.Auto;
import com.wangchao.server.client.ProductClient;
import com.wangchao.server.config.RestTemplateConfig;
import com.wangchao.server.dataobject.ProductInfo;
import com.wangchao.server.dto.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg(){
        // 第一种方式(直接使用 restTemplate,url写死)
//        RestTemplate restTemplate=new RestTemplate();
//        String result=restTemplate.getForObject("http://localhost:8083/msg",String.class);

        // 第二种方式(利用loadBalancerClient,通过应用名获取url,然后在使用restTemplate)
        ServiceInstance instance = loadBalancerClient.choose("PRODUCT");
//        String url=String.format("http://%s:%s",instance.getHost(),instance.getPort())+"/msg";
//        RestTemplate restTemplate=new RestTemplate();
//        String result=restTemplate.getForObject(url,String.class);

        // 第三种方式(利用LoadBalanced,可在restTemplate里使用应用的名字)
//        String result=restTemplate.getForObject("http://PRODUCT/msg",String.class);

        String result=productClient.productMsg();

        System.out.println(result);
        return result;
    }


    @GetMapping("/getProductList")
    public void listForOrder(){
        List<ProductInfo> productInfos = productClient.listForOrder(Arrays.asList("123456", "123457"));
        for (ProductInfo productInfo : productInfos) {
            System.out.println(productInfo.toString());
        }
    }


    @GetMapping("/decreaseStock")
    public void decreaseStock(){
        CartDTO cartDTO=new CartDTO("123457",5);
        List<CartDTO> list=new ArrayList<>();
        list.add(cartDTO);
        productClient.decreaseStock(list);
    }
}
