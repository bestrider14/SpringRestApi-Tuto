package com.codewithmosh.store.dtos.cart;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProductDto {
    Long id;
    String name;
    BigDecimal price;
}