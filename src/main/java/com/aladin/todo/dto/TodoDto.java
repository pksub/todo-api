package com.aladin.todo.dto;

import lombok.*;

@Data
@Builder
public class TodoDto {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
