package com.aureadigitallabs.aurea_api.service;

import com.aureadigitallabs.aurea_api.model.User;
import com.aureadigitallabs.aurea_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    // Lógica simple de login (verifica usuario y contraseña)
    public User login(String username, String password) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }
        return null;
    }

    public User updateUser(Long id, User userDetails) {
    return repository.findById(id).map(user -> {
        user.setUsername(userDetails.getUsername());
        // Solo actualizamos el rol si viene en la petición
        if (userDetails.getRole() != null) {
            user.setRole(userDetails.getRole());
        }
        // Opcional: Actualizar password solo si no está vacío
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(userDetails.getPassword()); // Recuerda encriptarla si usas BCrypt
        }
        return repository.save(user);
    }).orElse(null);
}
}