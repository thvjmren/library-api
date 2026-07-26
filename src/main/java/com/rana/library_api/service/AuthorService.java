package com.rana.library_api.service;

import com.rana.library_api.dto.AuthorDto;
import com.rana.library_api.entity.Author;
import com.rana.library_api.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Page<AuthorDto> getAllAuthors(Pageable pageable) {

        return authorRepository.findAll(pageable)
                .map(author -> new AuthorDto(
                        author.getId(),
                        author.getFullName(),
                        author.getEmail()
                ));
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