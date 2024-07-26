package com.tastecamp.api.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RecipeDTO { // Data Transfer Object
    @NotBlank
    @Size(max = 150)
    private String title;

    @NotBlank
    private String ingredients;

    @NotBlank
    private String steps;

    @NotNull
    private Long authorId;

    @NotEmpty
    private List<Long> categoryIds;
}
