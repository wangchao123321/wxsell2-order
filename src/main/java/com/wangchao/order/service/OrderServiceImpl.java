package com.wangchao.order.service;

import com.wangchao.order.dataobject.OrderMaster;
import com.wangchao.order.dto.OrderDTO;
import com.wangchao.order.enums.OrderStatusEnum;
import com.wangchao.order.enums.PayStatusEnum;
import com.wangchao.order.repository.OrderDetailRepository;
import com.wangchao.order.repository.OrderMasterRepository;
import com.wangchao.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        // todo 查询商品信息(调用商品服务)

        // todo 计算总价

        // todo 扣库存(调用商品服务)

        // 订单入库
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(KeyUtil.genUniqueKey());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(new BigDecimal(5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());


        orderMasterRepository.save(orderMaster);

        return orderDTO;
    }
}
