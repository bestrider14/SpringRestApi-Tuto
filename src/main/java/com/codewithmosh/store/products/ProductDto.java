package com.codewithmosh.store.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ProductDto {
    private Long id;

    @NotBlank(message = "Name cannot be null.")
    private String name;

    @NotBlank(message = "Description cannot be null.")
    private String description;

    @NotNull(message = "Price cannot be null.")
    private BigDecimal price;

    @NotNull(message = "CategoryId cannot be null.")
    private Byte categoryId;
}
