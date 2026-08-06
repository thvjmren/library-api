package com.rana.library_api.specification;

import com.rana.library_api.entity.Book;
import com.rana.library_api.entity.Category;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) -> {
            if (title == null || title.isBlank()) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, cb) -> {
            if (author == null || author.isBlank()) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get("author").get("fullName")),
                    "%" + author.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Book> hasCategory(String category) {
        return (root, query, cb) -> {
            if (category == null || category.isBlank()) {
                return null;
            }

            Join<Book, Category> categories = root.join("categories");

            return cb.like(
                    cb.lower(categories.get("name")),
                    "%" + category.toLowerCase() + "%"
            );
        };
    }
}