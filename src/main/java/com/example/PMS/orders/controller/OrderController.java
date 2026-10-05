package com.example.PMS.orders.controller;

import com.example.PMS.orders.dto.request.OrderRequest;
import com.example.PMS.common.response.BaseResponse;
import com.example.PMS.orders.dto.response.OrderResponse;
import com.example.PMS.orders.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BaseResponse<OrderResponse>> create(
            @Valid @RequestBody OrderRequest request
            ){
        OrderResponse response = orderService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.created("Order success.",response)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BaseResponse<List<OrderResponse>>> getAll(){
        List<OrderResponse> responses = orderService.findAll();
        return ResponseEntity.ok(
                BaseResponse.ok("Products retrieved success",responses)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BaseResponse<OrderResponse>> getById(@PathVariable Integer id){
        OrderResponse response = orderService.findById(id);
        return ResponseEntity.ok(
                BaseResponse.ok("Products retrieved success",response)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BaseResponse<OrderResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody OrderRequest request
    ){
        OrderResponse response = orderService.update(id,request);
        return ResponseEntity.ok(
                BaseResponse.ok("Order update success",response)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Integer id){
        orderService.delete(id);
        return ResponseEntity.ok(
                BaseResponse.ok("Order delete success",null)
        );
    }
 }
