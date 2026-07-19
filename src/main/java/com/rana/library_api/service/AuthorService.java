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
}