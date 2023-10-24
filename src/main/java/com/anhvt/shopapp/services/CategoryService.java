package com.anhvt.shopapp.services;

import com.anhvt.shopapp.dtos.CategoryDTO;
import com.anhvt.shopapp.models.Category;
import com.anhvt.shopapp.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = Category
                .builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(long categoryId, CategoryDTO categoryDTO) {
        Category existedCategory = getCategoryById(categoryId);
        existedCategory.setName(categoryDTO.getName());
        // Code update ...
        return existedCategory;
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
