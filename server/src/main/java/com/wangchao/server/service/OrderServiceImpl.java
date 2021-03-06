package com.wangchao.server.service;

import com.wangchao.server.client.ProductClient;
import com.wangchao.server.dataobject.OrderDetail;
import com.wangchao.server.dataobject.OrderMaster;
import com.wangchao.server.dataobject.ProductInfo;
import com.wangchao.server.dto.CartDTO;
import com.wangchao.server.dto.OrderDTO;
import com.wangchao.server.enums.OrderStatusEnum;
import com.wangchao.server.enums.PayStatusEnum;
import com.wangchao.server.repository.OrderDetailRepository;
import com.wangchao.server.repository.OrderMasterRepository;
import com.wangchao.server.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId=KeyUtil.genUniqueKey();

        //  查询商品信息(调用商品服务)
        List<String> productIdList=orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfo> productInfoList = productClient.listForOrder(productIdList);
        //  计算总价
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfo productInfo : productInfoList) {
                if(productInfo.getProductId().equals(orderDetail.getProductId())){
                    // 单价*数量
                    orderAmount=productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        //  扣库存(调用商品服务)
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productClient.decreaseStock(cartDTOList);

        // 订单入库
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(new BigDecimal(5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());


        orderMasterRepository.save(orderMaster);

        return orderDTO;
    }
}
