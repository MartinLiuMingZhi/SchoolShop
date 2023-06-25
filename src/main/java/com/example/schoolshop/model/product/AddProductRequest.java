package com.example.schoolshop.model.product;

import lombok.Data;

import java.util.Date;

@Data
public class AddProductRequest {
    private String name;
    private String description;
    private Double price;
    private String image;
    private String type;
    private Long stock;
}
