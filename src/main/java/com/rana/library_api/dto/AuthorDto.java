package com.rana.library_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDto {

    private Long id;

    @NotBlank(message = "Full Name is Required")
    @Size(min = 2, max = 100)
    private String fullName;

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email")
    private String email;
}