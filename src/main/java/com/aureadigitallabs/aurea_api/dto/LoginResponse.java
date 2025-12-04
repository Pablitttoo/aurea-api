package com.aureadigitallabs.aurea_api.dto;

import com.aureadigitallabs.aurea_api.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private User user;
    private String token;
}