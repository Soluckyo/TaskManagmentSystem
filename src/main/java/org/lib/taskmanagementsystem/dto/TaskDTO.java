package org.lib.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lib.taskmanagementsystem.entity.Priority;
import org.lib.taskmanagementsystem.entity.Status;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "сущность задачи")
public class TaskDTO {

    @Schema(description = "Заголовок задачи", example = "Задача №4")
    private String title;

    @Schema(description = "Тело задачи", example = "Сделать тестовое задание")
    private String body;

    @Schema(description = "Статус задачи")
    private Status status;

    @Schema(description = "Приоритет задачи")
    private Priority priority;

    @Schema(description = "Комментарий к задаче", example = "Приступлю к задаче 28.02")
    private String comment;
}
