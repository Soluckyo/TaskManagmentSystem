package org.lib.taskmanagementsystem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.lib.taskmanagementsystem.entity.Status;
import org.lib.taskmanagementsystem.service.AdminService;
import org.lib.taskmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User_controller")
@RestController
@RequestMapping("/api/v1/user/")
@AllArgsConstructor
@NoArgsConstructor
public class UserController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @Operation(
            summary = "Изменение задачи",
            description = "Принимает taskId из адреса и новый status, после обновляет поле задачи на новое значение"
    )
    @PutMapping("/change_status/{taskId}")
    public ResponseEntity<String> changeStatus(@PathVariable Long taskId, @RequestBody Status status) {
        try{
            userService.changeStatus(taskId, status);
            return ResponseEntity.status(HttpStatus.OK).body("Комментарий успешно добавлен");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/add_comment/{taskId}")
    @Operation(
            summary = "Добавление комментария к задаче",
            description = "Принимает taskId из адреса и comment, после обновляет поле задачи на новое значение"
    )
    public ResponseEntity<String> addComment(@PathVariable Long taskId,@RequestBody String comment) {
        try {
            adminService.addComment(taskId, comment);
            return ResponseEntity.status(HttpStatus.OK).body("Комментарий успешно добавлен");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
