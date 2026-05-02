package com.sistore.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String category ;

    private String brand;

    private Boolean available;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private InventoryResponse inventory;

}
