package com.example.schoolshop.model.cart;

import lombok.Data;

@Data
public class AddResponse {
    /**
     * cart_id
     */

    private Long cart_id;

    /**
     * user_id
     */

    private Long user_id;

    /**
     * product_id
     */

    private Long product_id;

    /**
     * 商品名称
     */

    private String product_name;

    /**
     * 商品图片
     */

    private String product_image;

    /**
     * 商品数量
     */

    private Long quantity;

    /**
     * 商品单价
     */

    private Double unit_price;

    /**
     * 商品总价
     */

    private Double total_price;
}
