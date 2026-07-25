package com.rana.library_api.service;

import com.rana.library_api.dto.BookDto;
import com.rana.library_api.entity.Book;
import com.rana.library_api.repository.BookRepository;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rana.library_api.entity.Author;
import com.rana.library_api.repository.AuthorRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,
                   AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
}

    public Page<BookDto> getAllBooks(Pageable pageable) {

        return bookRepository.findAll(pageable)
                .map(book -> new BookDto(
                        book.getId(),
                        book.getTitle(),
                        book.getIsbn(),
                        book.getAuthor() != null ? book.getAuthor().getId() : null
                ));
    }

    public BookDto getBookById(Long id) {

        Book book = bookRepository.findById(id).orElseThrow();

        BookDto dto = new BookDto();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());

        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
        }

        return dto;
    }

    public BookDto createBook(BookDto bookDto) {
        Book book = new Book();

        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());

        Author author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);

        return new BookDto(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getIsbn(),
                savedBook.getAuthor().getId()
        );
    }

    public BookDto updateBook(Long id, BookDto bookDto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());

        Author author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        book.setAuthor(author);

        Book updatedBook = bookRepository.save(book);

        return new BookDto(
                updatedBook.getId(),
                updatedBook.getTitle(),
                updatedBook.getIsbn(),
                updatedBook.getAuthor().getId()
        );
    }

    public void deleteBook(Long id) {

        bookRepository.deleteById(id);

    }
}