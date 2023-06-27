package com.example.schoolshop.model.cart;

import lombok.Data;

@Data
public class AddRequest {
    private Long user_id;
    private Long product_id;
    private Long num;
}
