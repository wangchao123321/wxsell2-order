package com.wangchao.server.service;

import com.wangchao.server.dto.OrderDTO;

public interface OrderService {

    // 创建订单
    OrderDTO create(OrderDTO orderDTO);
}
