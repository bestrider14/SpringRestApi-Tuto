package com.codewithmosh.store.orders;

import com.codewithmosh.store.products.ProductDto;
import lombok.Data;

@Data
public class OrderItemDto {
    private ProductDto product;
}
