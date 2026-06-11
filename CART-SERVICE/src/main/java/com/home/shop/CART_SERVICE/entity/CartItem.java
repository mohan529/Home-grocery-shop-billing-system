package com.home.shop.CART_SERVICE.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem implements Serializable {

    private Long productId;
    private String productName;
    private int quantity;
    private double price;


}

