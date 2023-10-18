package com.anhvt.shopapp.controllers;

import com.anhvt.shopapp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
//@Validated
public class CategoryController {
    @GetMapping("")
    public ResponseEntity<String> getAll( @RequestParam("page") int page,
                                          @RequestParam("limit") int limit )
    {
        return ResponseEntity.ok("chao ban haha");
    }

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
        return ResponseEntity.ok("");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id){
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        return ResponseEntity.ok("");
    }
}
