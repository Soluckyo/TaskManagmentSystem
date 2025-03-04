package org.lib.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Schema(description = "JWT ответ")
public class JwtResponseDTO{
    @Schema(description = "Токен доступа")
    private String accessToken;

    @Schema(description = "Токен обновления")
    private String refreshToken;
}
