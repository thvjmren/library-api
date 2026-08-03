package com.rana.library_api.service;

import com.rana.library_api.dto.BookDto;
import com.rana.library_api.entity.Author;
import com.rana.library_api.entity.Book;
import com.rana.library_api.entity.Category;
import com.rana.library_api.repository.AuthorRepository;
import com.rana.library_api.repository.BookRepository;
import com.rana.library_api.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       CategoryRepository categoryRepository) {

        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<BookDto> getAllBooks(Pageable pageable) {

        return bookRepository.findAll(pageable)
                .map(book -> new BookDto(
                        book.getId(),
                        book.getTitle(),
                        book.getIsbn(),
                        book.getAuthor() != null ? book.getAuthor().getId() : null,
                        book.getCategories()
                                .stream()
                                .map(Category::getId)
                                .toList()
                ));
    }

    public BookDto getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book Not Found."));

        BookDto dto = new BookDto();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());

        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
        }

        dto.setCategoryIds(
                book.getCategories()
                        .stream()
                        .map(Category::getId)
                        .toList()
        );

        return dto;
    }

    public BookDto createBook(BookDto bookDto) {

        Book book = new Book();

        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());

        Author author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author Not Found."));

        book.setAuthor(author);

        List<Category> categories = bookDto.getCategoryIds() == null
                ? List.of()
                : categoryRepository.findAllById(bookDto.getCategoryIds());

        book.setCategories(categories);

        Book savedBook = bookRepository.save(book);

        return new BookDto(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getIsbn(),
                savedBook.getAuthor().getId(),
                savedBook.getCategories()
                        .stream()
                        .map(Category::getId)
                        .toList()
        );
    }

    public BookDto updateBook(Long id, BookDto bookDto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book Not Found."));

        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());

        Author author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author Not Found."));

        book.setAuthor(author);

        List<Category> categories = bookDto.getCategoryIds() == null
                ? List.of()
                : categoryRepository.findAllById(bookDto.getCategoryIds());

        book.setCategories(categories);

        Book updatedBook = bookRepository.save(book);

        return new BookDto(
                updatedBook.getId(),
                updatedBook.getTitle(),
                updatedBook.getIsbn(),
                updatedBook.getAuthor().getId(),
                updatedBook.getCategories()
                        .stream()
                        .map(Category::getId)
                        .toList()
        );
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookDto> searchBooks(String title) {

        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(book -> new BookDto(
                        book.getId(),
                        book.getTitle(),
                        book.getIsbn(),
                        book.getAuthor() != null ? book.getAuthor().getId() : null,
                        book.getCategories()
                                .stream()
                                .map(Category::getId)
                                .toList()
                ))
                .toList();
        }
}