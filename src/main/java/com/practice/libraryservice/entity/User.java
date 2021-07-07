package com.practice.libraryservice.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@Setter
public class User {

    private int id;

    @ApiModelProperty(value = "first name of the user", required = true)
    @NotBlank
    private String firstName;

    @ApiModelProperty(value = "last name of the user", required = true)
    @NotBlank
    private String lastName;

    @ApiModelProperty(value = "first name of the user", required = true)
    @NotBlank
    @Email
    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}