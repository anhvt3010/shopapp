package com.anhvt.shopapp.services;

import com.anhvt.shopapp.dtos.CategoryDTO;
import com.anhvt.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId, CategoryDTO category);
    void deleteCategory(long id);
}
