package org.lib.taskmanagementsystem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.lib.taskmanagementsystem.entity.Status;
import org.lib.taskmanagementsystem.entity.User;
import org.lib.taskmanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@Tag(name = "User_controller")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Изменение задачи",
            description = "Принимает taskId из адреса и новый status, после обновляет поле задачи на новое значение"
    )
    @PutMapping("/change_status/{taskId}")
    public ResponseEntity<String> changeStatus(@PathVariable Long taskId,
                                               @AuthenticationPrincipal User user,
                                               @RequestBody Status status) {
        try{
            userService.changeStatus(taskId, user, status);
            return ResponseEntity.status(HttpStatus.OK).body("Статус задачи успешно обновлен");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Задача не найдена: " + e.getMessage());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Нет доступа для изменения статуса задачи");
        }
    }

    @PutMapping("/add_comment/{taskId}")
    @Operation(
            summary = "Добавление комментария к задаче",
            description = "Принимает taskId из адреса и comment, после обновляет поле задачи на новое значение"
    )
    public ResponseEntity<String> addComment(@PathVariable Long taskId,
                                             @AuthenticationPrincipal User user,
                                             @RequestBody String comment) {
        try {
            userService.addComment(taskId, user, comment);
            return ResponseEntity.status(HttpStatus.OK).body("Комментарий успешно добавлен");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Задача не найдена: " + e.getMessage());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Нет доступа для изменения статуса задачи");
        }
    }
}
