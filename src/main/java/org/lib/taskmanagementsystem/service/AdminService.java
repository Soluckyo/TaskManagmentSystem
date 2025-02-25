package org.lib.taskmanagementsystem.service;

import org.lib.taskmanagementsystem.entity.Task;
import org.lib.taskmanagementsystem.entity.User;
import org.lib.taskmanagementsystem.repository.TaskRepo;
import org.lib.taskmanagementsystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private UserRepo userRepo;

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepo.findById(id).orElse(null);
    }

    public void addTask(Task task) {
        taskRepo.save(task);
    }

    public void updateTask(Task task) {
        taskRepo.save(task);
    }

    public void deleteTask(Long id) {
        taskRepo.deleteById(id);
    }

    public void assignTaskToUser(Long userId, Long taskId) {
        User user = findUserById(userId);
        Task task = findTaskById(taskId);
        if(user != null && task != null) {
            if(task.getUser() != null){
                task.setUser(null);
            }
            List<Task> listTask = Collections.singletonList(task);
            user.setTask(listTask);
            userRepo.save(user);

            task.setUser(user);
            taskRepo.save(task);
        }
    }

    public User findUserById(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }
}
