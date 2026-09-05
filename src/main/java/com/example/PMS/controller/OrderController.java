package com.example.PMS.controller;

import com.example.PMS.dto.request.OrderRequest;
import com.example.PMS.dto.response.BaseResponse;
import com.example.PMS.dto.response.OrderResponse;
import com.example.PMS.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<BaseResponse<OrderResponse>> create(
            @Valid @RequestBody OrderRequest request
            ){
        OrderResponse response = orderService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.created("Order create success.",response)
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<OrderResponse>>> getAll(){
        List<OrderResponse> response = orderService.findAll();
        return ResponseEntity.ok(
                BaseResponse.ok("Order retrieved success",response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<OrderResponse>> getById(@PathVariable Integer id){
        OrderResponse response = orderService.findById(id);
        return ResponseEntity.ok(
                BaseResponse.ok("Order retrieved success",response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<OrderResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody OrderRequest request
            ){
        OrderResponse response = orderService.update(id,request);
        return ResponseEntity.ok(
                BaseResponse.ok("Order updated success.",response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Integer id ){
        orderService.delete(id);
        return ResponseEntity.ok(
                BaseResponse.ok("Order deleted success.",null)
        );
    }

}
