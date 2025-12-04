package com.aureadigitallabs.aurea_api.repository;

import com.aureadigitallabs.aurea_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring crea la consulta automáticamente
    Optional<User> findByUsername(String username);
}