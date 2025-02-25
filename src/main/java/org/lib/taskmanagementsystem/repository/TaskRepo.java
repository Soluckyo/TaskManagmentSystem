package org.lib.taskmanagementsystem.repository;

import org.lib.taskmanagementsystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
