package org.lib.taskmanagementsystem.api;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.lib.taskmanagementsystem.dto.TaskAssignDTO;
import org.lib.taskmanagementsystem.dto.TaskDTO;
import org.lib.taskmanagementsystem.entity.Task;
import org.lib.taskmanagementsystem.entity.User;
import org.lib.taskmanagementsystem.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/")
@NoArgsConstructor
@AllArgsConstructor
public class AdminController {

    AdminService adminService;

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks() {
        adminService.getAllTasks();
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllTasks());
    }

    @PostMapping("/add_task")
    public ResponseEntity<String> addTask(@RequestBody TaskDTO taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setBody(taskDto.getBody());
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());
        task.setComment(taskDto.getComment());

        adminService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body("Задача успешно добавлена");
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<String> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDto) {

        Task task = adminService.findTaskById(taskId);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Задача не найдена");
        }
        task.setTitle(taskDto.getTitle());
        task.setBody(taskDto.getBody());
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());
        task.setComment(taskDto.getComment());

        adminService.updateTask(taskId, task);
        return ResponseEntity.status(HttpStatus.OK).body("Задача успешно обновлена");
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        adminService.deleteTask(taskId);
        return ResponseEntity.status(HttpStatus.OK).body("Задача успешно удалена");
    }

    @PutMapping("/assign")
    public ResponseEntity<String> assignTaskToUser(@RequestBody TaskAssignDTO taskAssignDTO) {
        Optional<Task> taskOpt = Optional.ofNullable(adminService.findTaskById(taskAssignDTO.getTaskId()));
        if (taskOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Задача не найдена");
        }

        Optional<User> userOpt = Optional.ofNullable(adminService.findUserById(taskAssignDTO.getUserId()));
        if (userOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }

        adminService.assignTaskToUser(userOpt.get(), taskOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Исполнитель задачи успешно назначен");
    }

    @PutMapping("/add_comment/{taskId}")
    public ResponseEntity<String> addComment(@PathVariable Long taskId,@RequestBody String comment) {
        try {
            adminService.addComment(taskId, comment);
            return ResponseEntity.status(HttpStatus.OK).body("Комментарий успешно добавлен");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
