package org.lib.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.lib.taskmanagementsystem.entity.Status;
import org.lib.taskmanagementsystem.entity.Task;
import org.lib.taskmanagementsystem.repository.TaskRepo;
import org.lib.taskmanagementsystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    @Autowired
    private TaskRepo taskRepo;

    public void changeStatus(Long taskId,Status status) {
        Optional<Task> taskOptional = taskRepo.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(status);
            taskRepo.save(task);
        }else throw new EntityNotFoundException("Task with id: " + taskId + " not found");
    }
}
