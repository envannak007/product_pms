package com.example.PMS.service;

import com.example.PMS.dto.request.OrderItemRequest;
import com.example.PMS.dto.response.OrderItemResponse;

import java.util.List;

public interface OrderItemService {
    OrderItemResponse create(OrderItemRequest request);
    List<OrderItemResponse> findAll();
    OrderItemResponse findById(Integer id);
    OrderItemResponse update(Integer id,OrderItemRequest request);
    void delete(Integer id);
}
