package org.example.varb.service;


import org.example.varb.entity.Category;
import org.example.varb.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> list() {
        return categoryRepository.findAll();
    }

    public Optional<Category> get(Long id) {
        return categoryRepository.findById(id);
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(cat -> {
                    cat.setName(updatedCategory.getName());
                    cat.setCode(updatedCategory.getCode());
                    cat.setUpdatedAt(updatedCategory.getUpdatedAt());
                    return categoryRepository.save(cat);
                })
                .orElse(null);
    }

    public boolean delete(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
