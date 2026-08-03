package com.rana.library_api.repository;

import com.rana.library_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {


    @Query("""
            SELECT DISTINCT b FROM Book b
            WHERE (:title IS NULL OR 
                   LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
            AND (:author IS NULL OR 
                   LOWER(b.author.fullName) LIKE LOWER(CONCAT('%', :author, '%')))
            AND (:category IS NULL OR EXISTS (
                   SELECT c FROM b.categories c
                   WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :category, '%'))
            ))
            """)
    List<Book> searchBooks(
            @Param("title") String title,
            @Param("author") String author,
            @Param("category") String category
    );
}   