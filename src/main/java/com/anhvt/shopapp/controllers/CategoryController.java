package com.anhvt.shopapp.controllers;

import com.anhvt.shopapp.dtos.CategoryDTO;
import com.anhvt.shopapp.models.Category;
import com.anhvt.shopapp.responses.UpdateCategoryResponse;
import com.anhvt.shopapp.services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private MessageSource messageSource;
    private final LocaleResolver localeResolver;

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
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAll( @RequestParam("page") int page,
                                          @RequestParam("limit") int limit )
    {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UpdateCategoryResponse> update(@PathVariable int id,
                                                         @Valid @RequestBody CategoryDTO categoryDTO,
                                                         HttpServletRequest request){
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(UpdateCategoryResponse.builder()
                        .message(messageSource.getMessage(
                                "category.update_category.update_successfully",
                                null,
                                localeResolver.resolveLocale(request)))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete Category successfully !");
    }
}
