package org.lib.taskmanagementsystem.dto;

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
public class TaskDTO {

    private Long id;

    private String title;

    private String body;

    private Status status;

    private Priority priority;

    private String comment;
}
