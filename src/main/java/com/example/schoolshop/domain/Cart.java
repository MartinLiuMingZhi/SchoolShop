package com.example.schoolshop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    @TableId(value = "cartId",type = IdType.AUTO)
    private Long cartId;

    /**
     * user_id
     */
    @TableField(value = "userId")
    private Long userid;

    /**
     * product_id
     */
    @TableField(value = "productId")
    private Long productId;

    /**
     * 商品名称
     */
    @TableField(value = "productName")
    private String productName;

    /**
     * 商品图片
     */
    @TableField(value = "productImage")
    private String productImage;

    /**
     * 商品数量
     */
    @TableField(value = "quantity")
    private Long quantity;

    /**
     * 商品单价
     */
    @TableField(value = "unitPrice")
    private Double unitPrice;

    /**
     * 商品总价
     */
    @TableField(value = "totalPrice")
    private Double totalPrice;

    @TableField(value = "createdTime")
    private Date createdTime;

    @TableField(value = "updatedTime")
    private Date updatedTime;



}
