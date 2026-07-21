package com.rana.library_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    private Long id;

    @NotBlank(message = "Title is Required")
    @Size(min = 2, max = 200)
    private String title;

    @NotBlank(message = "ISBN is Required")
    private String isbn;

    @NotNull(message = "Author ID is Required")
    private Long authorId;
}