package com.rana.library_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Full Name is Required")
    @Size(min = 2, max = 100)
    private String fullName;

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email")
    private String email;
}