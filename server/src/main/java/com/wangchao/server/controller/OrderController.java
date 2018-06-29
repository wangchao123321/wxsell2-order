package com.wangchao.server.controller;

import com.wangchao.server.converter.OrderForm2OrderDTOConverter;
import com.wangchao.server.dataobject.OrderDetail;
import com.wangchao.server.dto.OrderDTO;
import com.wangchao.server.enums.ResultEnum;
import com.wangchao.server.exception.OrderException;
import com.wangchao.server.form.OrderForm;
import com.wangchao.server.service.OrderService;
import com.wangchao.server.utils.ResultVOUtil;
import com.wangchao.server.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        // orderForm -> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车信息为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }

        OrderDTO result = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResultVOUtil.success(map);
    }

//    @PostMapping("/create")
//    public void create(OrderDTO orderDTO){
////        System.out.println("1111"+orderDTO.toString());
////        OrderDTO orderDTO=new OrderDTO();
////        orderDTO.setBuyerName("张三");
////        orderDTO.setBuyerPhone("1231231231");
////        orderDTO.setBuyerAddress("3232121421");
////        orderDTO.setBuyerOpenid("55555555555555");
////
////        List<OrderDetail> orderDetails=new ArrayList<>();
////        OrderDetail orderDetail1=new OrderDetail();
////        orderDetail1.setProductId("123456");
////        orderDetail1.setProductQuantity(10);
////        orderDetails.add(orderDetail1);
////        orderDTO.setOrderDetailList(orderDetails);
//        orderService.create(orderDTO);
//    }
}
