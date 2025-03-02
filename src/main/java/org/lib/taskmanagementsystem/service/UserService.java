package org.lib.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.lib.taskmanagementsystem.entity.Role;
import org.lib.taskmanagementsystem.entity.Status;
import org.lib.taskmanagementsystem.entity.Task;
import org.lib.taskmanagementsystem.entity.User;
import org.lib.taskmanagementsystem.repository.TaskRepo;
import org.lib.taskmanagementsystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private UserRepo userRepo;

    public void changeStatus(Long taskId, User user, Status status) throws AccessDeniedException {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Задача с ID: " + taskId + " не найдена"));
        if (!task.getUser().equals(user)) {
            throw new AccessDeniedException(" Вы не можете редактировать эту задачу");
        }
        task.setStatus(status);
        taskRepo.save(task);
    }

    public void addComment(Long taskId,User user, String comment) throws AccessDeniedException {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Задача с ID: " + taskId + " не найдена"));
        if (!task.getUser().equals(user)) {
            throw new AccessDeniedException(" Вы не можете редактировать эту задачу");
        }
        task.setComment(comment);
        taskRepo.save(task);
    }

    public Optional<User> getByEmail(String email) {
        return userRepo.getByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
