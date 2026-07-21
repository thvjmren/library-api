package com.rana.library_api.controller;

import com.rana.library_api.dto.AuthorDto;
import com.rana.library_api.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<Page<AuthorDto>> getAllAuthors(Pageable pageable) {
        return ResponseEntity.ok(authorService.getAllAuthors(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorDto authorDto) {
        AuthorDto createdAuthor = authorService.createAuthor(authorDto);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorDto authorDto) {

        return ResponseEntity.ok(authorService.updateAuthor(id, authorDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
} 