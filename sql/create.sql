create database SchoolShop;

use SchoolShop;

-- 用户表
create table if not exists user
(
    id          bigint                  auto_increment comment '用户id' primary key,
    username    varchar(256)            not null comment '用户名',
    password    varchar(512)            not null comment '密码',
    name        varchar(256)            null comment '姓名',
    sex         varchar(256)            null comment '性别',
    email       varchar(256)            not null comment '邮箱',
    phone       varchar(256)            null comment '电话号码',
    birthday    date                    not null comment '出生日期',
    state       int                     not null comment '状态码'
) comment '用户' collate = utf8mb4_unicode_ci;


-- 商品表
create table if not exists product
(
    id          bigint                  auto_increment comment '商品id' primary key,
    name        varchar(255)            not null comment '商品名称',
    description text                    comment '商品描述',
    price       decimal(10,2)           not null comment '商品价格',
    image       varchar(255)            not null comment '商品图片',
    type        varchar(50)             not null comment '商品类型',
    stock       bigint                  not null comment '商品库存',
    date        date                    not null comment '入库日期'
)comment '商品' collate = utf8mb4_unicode_ci;

-- 购物车表
create table if not exists cart
(
    cart_id          bigint                  auto_increment comment '购物车id',
    user_id          bigint                  not null comment '用户id',
    product_id       bigint                  not null comment '商品id',
    product_name     varchar(255)            not null comment '商品名称',
    product_image    varchar(255)            not null comment '商品图片',
    quantity         bigint                  not null comment '商品数量',
    unit_price      decimal(10,2)            not null comment '商品单价',
    total_price     decimal(10,2)            not null comment '商品总价',
    created_time    timestamp default current_timestamp comment '创建时间',
    updated_time    timestamp default  current_timestamp on update current_timestamp comment '更新时间',
    primary key (cart_id),
    index (user_id),
    index (product_id)
)