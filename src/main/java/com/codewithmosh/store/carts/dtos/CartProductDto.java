package com.codewithmosh.store.carts.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProductDto {
    Long id;
    String name;
    BigDecimal price;
}