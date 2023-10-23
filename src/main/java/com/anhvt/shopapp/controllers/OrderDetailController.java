package com.anhvt.shopapp.controllers;

import com.anhvt.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    @PostMapping("")
    public ResponseEntity<?> addOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO){
        return ResponseEntity.ok("add successfully");

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok("get by id successfully");
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<List<?>> getOrderDetailByOrder( @Valid @PathVariable("order_id") Long id){
        return ResponseEntity.ok(Collections.singletonList("getOrderDetailByOrder successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") Long id,
                                    @RequestBody OrderDetailDTO orderDetailDTO){
        return ResponseEntity.ok("update successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok("delete successfully");
    }
}
