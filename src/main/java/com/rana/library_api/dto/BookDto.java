package com.rana.library_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Title is Required")
    @Size(min = 2, max = 200)
    private String title;

    @NotBlank(message = "ISBN is Required")
    private String isbn;

    @NotNull(message = "Author ID is Required")
    private Long authorId;

    private List<Long> categoryIds;
}