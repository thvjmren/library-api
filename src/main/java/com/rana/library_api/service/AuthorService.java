package com.rana.library_api.service;

import com.rana.library_api.dto.AuthorDto;
import com.rana.library_api.entity.Author;
import com.rana.library_api.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> getAllAuthors() {

        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> authorDtos = new ArrayList<>();

        for (Author author : authors) {

            AuthorDto dto = new AuthorDto();

            dto.setId(author.getId());
            dto.setFullName(author.getFullName());
            dto.setEmail(author.getEmail());

            authorDtos.add(dto);
        }

        return authorDtos;
    }

    public AuthorDto getAuthorById(Long id) {

        Author author = authorRepository.findById(id).orElseThrow();

        AuthorDto dto = new AuthorDto();

        dto.setId(author.getId());
        dto.setFullName(author.getFullName());
        dto.setEmail(author.getEmail());

        return dto;
    }

    public AuthorDto createAuthor(AuthorDto authorDto) {

        Author author = new Author();

        author.setFullName(authorDto.getFullName());
        author.setEmail(authorDto.getEmail());

        Author savedAuthor = authorRepository.save(author);

        return new AuthorDto(
                savedAuthor.getId(),
                savedAuthor.getFullName(),
                savedAuthor.getEmail()
        );
    }

    public AuthorDto updateAuthor(Long id, AuthorDto authorDto) {

        Author author = authorRepository.findById(id).orElseThrow();

        author.setFullName(authorDto.getFullName());
        author.setEmail(authorDto.getEmail());

        Author updatedAuthor = authorRepository.save(author);

        return new AuthorDto(
                updatedAuthor.getId(),
                updatedAuthor.getFullName(),
                updatedAuthor.getEmail()
        );
    }

    public void deleteAuthor(Long id) {

        authorRepository.deleteById(id);

    }
}