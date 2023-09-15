package com.example.schoolshop.model.manager;

import lombok.Data;

@Data
public class ChangePassword {
    String password;
    String newPassword;
    String confirmPassword;
}
