package com.example.schoolshop.domain;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "orders")
@Data
public class Order implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(value = "productId")
    private Long productId;

    @TableField(value = "userId")
    private Long userId;

    @TableField(value = "quantity")
    private int quantity;

    @TableField(value = "price")
    private double price;

    @TableField(value = "status")
    private int status;

    @TableField(value = "createTime")
    private Date createTime;

    @TableField(value = "updateTime")
    private Date updateTime;
}
