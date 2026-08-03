package com.rana.library_api.service;

import com.rana.library_api.dto.CategoryDto;
import com.rana.library_api.entity.Category;
import com.rana.library_api.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getName()
                ))
                .toList();
    }

    public CategoryDto getCategoryById(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow();

        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {

        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();
        category.setName(categoryDto.getName());

        Category savedCategory = categoryRepository.save(category);

        return new CategoryDto(
                savedCategory.getId(),
                savedCategory.getName()
        );
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {

        Category category = categoryRepository.findById(id).orElseThrow();

        category.setName(categoryDto.getName());

        Category updatedCategory = categoryRepository.save(category);

        return new CategoryDto(
                updatedCategory.getId(),
                updatedCategory.getName()
        );
    }

    public void deleteCategory(Long id) {

        categoryRepository.deleteById(id);
    }
}