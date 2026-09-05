package com.example.PMS.controller;

import com.example.PMS.dto.request.OrderItemRequest;
import com.example.PMS.dto.response.BaseResponse;
import com.example.PMS.dto.response.OrderItemResponse;
import com.example.PMS.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order_item")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<BaseResponse<OrderItemResponse>> create(
            @Valid @RequestBody OrderItemRequest request
            ){
        OrderItemResponse response = orderItemService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.created("Order item created success.",response)
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<OrderItemResponse>>> getAll(){
        List<OrderItemResponse> response = orderItemService.findAll();
        return ResponseEntity.ok(
                BaseResponse.ok("Order items retrieved success.",response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<OrderItemResponse>> getById(@PathVariable Integer id){
        OrderItemResponse response = orderItemService.findById(id);
        return ResponseEntity.ok(
                BaseResponse.ok("Order items retrieved success.",response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<OrderItemResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody OrderItemRequest request
    ){
        OrderItemResponse response = orderItemService.update(id,request);
        return ResponseEntity.ok(
                BaseResponse.ok("Order items updated success.",response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Integer id){
        orderItemService.delete(id);
        return ResponseEntity.ok(
                BaseResponse.ok("Order items deleted success.",null)
        );
    }
}
