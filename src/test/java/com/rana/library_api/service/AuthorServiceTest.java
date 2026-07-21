package com.rana.library_api.service;

import com.rana.library_api.dto.AuthorDto;
import com.rana.library_api.entity.Author;
import com.rana.library_api.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;


    @Test
    void shouldGetAllAuthors() {

        Author author = new Author();
        author.setId(1L);
        author.setFullName("Test Author");
        author.setEmail("test@gmail.com");

        Page<Author> page = new PageImpl<>(List.of(author));

        when(authorRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        Page<AuthorDto> result =
                authorService.getAllAuthors(PageRequest.of(0,10));


        assertEquals(1, result.getContent().size());
        assertEquals("Test Author",
                result.getContent().get(0).getFullName());

        verify(authorRepository)
        .findAll(any(Pageable.class));
    }


    @Test
    void shouldGetAuthorById() {

        Author author = new Author();
        author.setId(1L);
        author.setFullName("John");
        author.setEmail("john@gmail.com");


        when(authorRepository.findById(1L))
                .thenReturn(Optional.of(author));


        AuthorDto result =
                authorService.getAuthorById(1L);


        assertEquals(1L, result.getId());
        assertEquals("John", result.getFullName());
        assertEquals("john@gmail.com", result.getEmail());

        verify(authorRepository)
                .findById(1L);
    }


    @Test
    void shouldCreateAuthor() {

        AuthorDto dto = new AuthorDto();
        dto.setFullName("New Author");
        dto.setEmail("new@gmail.com");


        Author saved = new Author();
        saved.setId(1L);
        saved.setFullName("New Author");
        saved.setEmail("new@gmail.com");


        when(authorRepository.save(any(Author.class)))
                .thenReturn(saved);


        AuthorDto result =
                authorService.createAuthor(dto);


        assertEquals(1L, result.getId());
        assertEquals("New Author", result.getFullName());

        verify(authorRepository)
                .save(any(Author.class));
    }


    @Test
    void shouldUpdateAuthor() {

        Author author = new Author();
        author.setId(1L);
        author.setFullName("Old Name");
        author.setEmail("old@gmail.com");


        AuthorDto dto = new AuthorDto();
        dto.setFullName("Updated Name");
        dto.setEmail("updated@gmail.com");


        when(authorRepository.findById(1L))
                .thenReturn(Optional.of(author));

        when(authorRepository.save(any(Author.class)))
                .thenReturn(author);


        AuthorDto result =
                authorService.updateAuthor(1L, dto);


        assertEquals("Updated Name",
                result.getFullName());

        verify(authorRepository)
                .save(any(Author.class));
    }


    @Test
    void shouldDeleteAuthor() {

        doNothing()
                .when(authorRepository)
                .deleteById(1L);


        authorService.deleteAuthor(1L);


        verify(authorRepository)
                .deleteById(1L);
    }
}