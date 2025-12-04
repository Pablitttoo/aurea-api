package com.aureadigitallabs.aurea_api.controller;

import com.aureadigitallabs.aurea_api.dto.LoginRequest;
import com.aureadigitallabs.aurea_api.model.User;
import com.aureadigitallabs.aurea_api.model.UserRole;
import com.aureadigitallabs.aurea_api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; 
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean; 
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Funciona nativo en Spring Boot 3.4.0
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_ShouldReturnToken_WhenCredentialsValid() throws Exception {
        User mockUser = new User(1L, "user", "pass", UserRole.CLIENT);
        when(userService.login("user", "pass")).thenReturn(mockUser);

        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("pass");

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.user.username").value("user"));
    }

    @Test
    void login_ShouldReturn401_WhenCredentialsInvalid() throws Exception {
        when(userService.login(anyString(), anyString())).thenReturn(null);

        LoginRequest request = new LoginRequest();
        request.setUsername("bad");
        request.setPassword("bad");

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}