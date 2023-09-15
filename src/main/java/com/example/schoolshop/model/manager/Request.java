package com.example.schoolshop.model.manager;

import lombok.Data;

@Data
public class Request {
    private String username;
    private String password;
    private String checkPassword;
}
