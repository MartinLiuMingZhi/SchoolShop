package com.example.schoolshop.model.product;

import lombok.Data;

@Data
public class UpdateProductRequest {
    private String name;
    private String description;
    private Double price;
    private String image;
    private String type;
    private Long stock;
}
