package com.aureadigitallabs.aurea_api.service;

import com.aureadigitallabs.aurea_api.model.User;
import com.aureadigitallabs.aurea_api.model.UserRole;
import com.aureadigitallabs.aurea_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    // --- Pruebas de Login (Lógica Crítica) ---

    @Test
    void login_ShouldReturnUser_WhenCredentialsAreCorrect() {
        // Arrange
        User mockUser = new User(1L, "admin", "12345", UserRole.ADMIN);
        when(repository.findByUsername("admin")).thenReturn(Optional.of(mockUser));

        // Act
        User result = service.login("admin", "12345");

        // Assert
        assertNotNull(result);
        assertEquals("admin", result.getUsername());
    }

    @Test
    void login_ShouldReturnNull_WhenUserDoesNotExist() {
        when(repository.findByUsername("fantasma")).thenReturn(Optional.empty());

        User result = service.login("fantasma", "12345");

        assertNull(result);
    }

    @Test
    void login_ShouldReturnNull_WhenPasswordIsIncorrect() {
        User mockUser = new User(1L, "admin", "12345", UserRole.ADMIN);
        when(repository.findByUsername("admin")).thenReturn(Optional.of(mockUser));

        // Probamos con contraseña incorrecta
        User result = service.login("admin", "wrong_password");

        assertNull(result);
    }

    // --- Pruebas CRUD básicas para sumar cobertura ---

    @Test
    void shouldGetAllUsers() {
        when(repository.findAll()).thenReturn(Arrays.asList(new User(), new User()));
        List<User> users = service.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void shouldSaveUser() {
        User user = new User(null, "new", "pass", UserRole.CLIENT);
        when(repository.save(user)).thenReturn(user);
        
        User saved = service.saveUser(user);
        assertNotNull(saved);
    }

    @Test
    void shouldDeleteUser() {
        doNothing().when(repository).deleteById(1L);
        service.deleteUser(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}