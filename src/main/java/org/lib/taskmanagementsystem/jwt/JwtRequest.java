package org.lib.taskmanagementsystem.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "JWT запрос")
public class JwtRequest {

    @Schema(description = "Почта", example = "user@gmail.com")
    private String email;

    @Schema(description = "Пароль", example = "password")
    private String password;
}
