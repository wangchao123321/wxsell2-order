package com.wangchao.order.service;

import com.wangchao.order.dto.OrderDTO;

public interface OrderService {

    // 创建订单
    OrderDTO create(OrderDTO orderDTO);
}
