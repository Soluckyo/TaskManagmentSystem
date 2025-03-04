package org.lib.taskmanagementsystem.repository;

import org.lib.taskmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> getByEmail(String email);

    Optional <User> findByEmail(String email);
}
