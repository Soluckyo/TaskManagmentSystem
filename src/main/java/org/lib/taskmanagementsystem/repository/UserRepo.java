package org.lib.taskmanagementsystem.repository;

import org.lib.taskmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
