package com.example.schoolshop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品
 * @TableName product
 */
@TableName(value = "product")
@Data
public class product implements Serializable {
    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     *商品名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 商品描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 商品价格
     */
    @TableField(value = "price")
    private Double price;

    /**
     * 商品图片
     */
    @TableField(value = "image")
    private String image;

    /**
     * 商品类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 商品库存
     */
    @TableField(value = "stock")
    private Long stock;

    /**
     * 入库日期
     */
    @TableField(value = "date")
    private Date date;

}
