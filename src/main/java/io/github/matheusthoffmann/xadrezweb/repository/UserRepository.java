package io.github.matheusthoffmann.xadrezweb.repository;

import io.github.matheusthoffmann.xadrezweb.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}