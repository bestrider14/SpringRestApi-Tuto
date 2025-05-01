package com.codewithmosh.store.carts.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddItemToCartRequest {
    @NotBlank(message = "ProductId cannot be null.")
    private Long productId;
}
