package com.wangchao.common;

import lombok.Data;

@Data
public class DecreaseStockInput {

    // 商品id
    private String productId;

    // 商品数量
    private Integer productQuantity;

    public DecreaseStockInput(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
