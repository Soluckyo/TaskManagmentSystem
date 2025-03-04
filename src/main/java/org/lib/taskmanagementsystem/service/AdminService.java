package org.lib.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.lib.taskmanagementsystem.entity.Task;
import org.lib.taskmanagementsystem.entity.User;
import org.lib.taskmanagementsystem.repository.TaskRepo;
import org.lib.taskmanagementsystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final TaskRepo taskRepo;

    private final UserRepo userRepo;

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepo.findById(id).orElse(null);
    }

    public void addTask(Task task) {
        taskRepo.save(task);
    }

    public void updateTask(Long taskId, Task task) {
        Task taskToUpdate = findTaskById(taskId);
        if (taskToUpdate != null) {
            taskRepo.saveAndFlush(task);
        }else throw new EntityNotFoundException("Task with id: " + task.getId() + " not found");
    }

    public void deleteTask(Long id) {
        taskRepo.deleteById(id);
    }

    public void assignTaskToUser(User user, Task task) {
        if(user == null || task == null) {
            throw new EntityNotFoundException
                    ("Задача или Пользователь не могут быть null");
        }

            if(task.getUser() != null){
                task.setUser(null);
            }

            user.getTask().add(task);
            userRepo.save(user);

            task.setUser(user);
            taskRepo.save(task);
    }

    public User findUserById(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public void addComment(Long taskId, String comment) {
        Optional<Task> task = Optional.ofNullable(findTaskById(taskId));
        if (task.isPresent()) {
            task.get().setComment(comment);
            taskRepo.save(task.get());
        }else throw new EntityNotFoundException("Task with id: " + taskId + " not found");
    }
}
