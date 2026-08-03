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
                .map(this::mapToDto);
    }


    public BookDto getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book Not Found."));

        return mapToDto(book);
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


        return mapToDto(bookRepository.save(book));
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


        return mapToDto(bookRepository.save(book));
    }


    public void deleteBook(Long id) {

        bookRepository.deleteById(id);
    }


    public List<BookDto> searchBooks(
            String title,
            String author,
            String category) {

        return bookRepository.searchBooks(title, author, category)
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    private BookDto mapToDto(Book book) {

        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),

                book.getAuthor() != null
                        ? book.getAuthor().getId()
                        : null,

                book.getCategories()
                        .stream()
                        .map(Category::getId)
                        .toList()
        );
    }
}