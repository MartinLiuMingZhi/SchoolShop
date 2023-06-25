package com.example.schoolshop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 购物车
 * @TableName cart
 */
@TableName(value = "cart")
@Data
public class Cart implements Serializable {

    /**
     * cart_id
     */
    @TableId(value = "cart_id",type = IdType.AUTO)
    private Long cart_id;

    /**
     * user_id
     */
    @TableField(value = "user_id")
    private Long user_id;

    /**
     * product_id
     */
    @TableField(value = "product_id")
    private Long product_id;

    /**
     * 商品名称
     */
    @TableField(value = "product_name")
    private String product_name;

    /**
     * 商品图片
     */
    @TableField(value = "product_image")
    private String product_image;

    /**
     * 商品数量
     */
    @TableField(value = "quantity")
    private Long quantity;

    /**
     * 商品单价
     */
    @TableField(value = "unit_price")
    private Double unit_price;

    /**
     * 商品总价
     */
    @TableField(value = "total_price")
    private Double total_price;
}
