package org.lib.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.lib.taskmanagementsystem.entity.Role;

@Getter
@Setter
@Schema(description = "Сущность пользователя для регистрации")
public class RegistrationUserDTO {

    @Schema(description = "Юзернейм", example = "user")
    private String username;

    @Schema(description = "Почта пользователя", example = "user@gmail.com")
    private String email;

    @Schema(description = "пароль", example = "password")
    private String password;

    @Schema(description = "Роль пользователя")
    private Role role;
}
