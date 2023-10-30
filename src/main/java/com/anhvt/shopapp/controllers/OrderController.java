package com.anhvt.shopapp.controllers;

import com.anhvt.shopapp.dtos.OrderDTO;
import com.anhvt.shopapp.responses.OrderResponse;
import com.anhvt.shopapp.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.ExportException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;
    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid OrderDTO orderDTO,
                                 BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage) // lay ra message error da quy dinh
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            OrderResponse orderResponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("user_id") Long userId){
        try {
            return ResponseEntity.ok("get order ok ");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("get order failed");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") Long id,
                                    @Valid @RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok("update order successfully !");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok("delete order successfully !");
    }
}
