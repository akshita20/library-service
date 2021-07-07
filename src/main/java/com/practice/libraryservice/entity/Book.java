package com.practice.libraryservice.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Book {

    private int id;

    @ApiModelProperty(value = "name of the book", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "name of the author", required = true)
    @NotBlank
    private String author;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
