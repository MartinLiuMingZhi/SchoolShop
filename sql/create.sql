create database SchoolShop;

use SchoolShop;

-- 用户表
create table if not exists user
(
    id          bigint                  auto_increment comment 'id' primary key,
    username    varchar(256)            not null comment '用户名',
    password    varchar(512)            not null comment '密码',
    name        varchar(256)            null comment '姓名',
    sex         varchar(256)            null comment '性别',
    email       varchar(256)            not null comment '邮箱',
    phone       varchar(256)            null comment '电话号码',
    birthday    date                    not null comment '生日',
    state       int                     not null comment '状态码',
    code        varchar(256)            null comment '验证码'
) comment '用户' collate = utf8mb4_unicode_ci;
