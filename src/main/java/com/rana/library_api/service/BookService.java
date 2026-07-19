package com.rana.library_api.service;

import com.rana.library_api.dto.BookDto;
import com.rana.library_api.entity.Book;
import com.rana.library_api.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAllBooks() {

        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = new ArrayList<>();

        for (Book book : books) {

            BookDto dto = new BookDto();

            dto.setId(book.getId());
            dto.setTitle(book.getTitle());
            dto.setIsbn(book.getIsbn());

            if (book.getAuthor() != null) {
                dto.setAuthorId(book.getAuthor().getId());
            }

            bookDtos.add(dto);
        }

        return bookDtos;
    }
}