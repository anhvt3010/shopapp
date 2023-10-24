package com.anhvt.shopapp.controllers;

import com.anhvt.shopapp.dtos.CategoryDTO;
import com.anhvt.shopapp.models.Category;
import com.anhvt.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
//@Validated
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> add(@Valid @RequestBody CategoryDTO categoryDTO,
                                 BindingResult result){
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage) // lay ra message error da quy dinh
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("Create Category successfully !");
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAll( @RequestParam("page") int page,
                                          @RequestParam("limit") int limit )
    {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id,
                                         @Valid @RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("Update Category successfully !");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete Category successfully !");
    }
}
