package org.lib.taskmanagementsystem.repository;

import org.lib.taskmanagementsystem.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
    Page<Task> findAll(Pageable pageable);
}
