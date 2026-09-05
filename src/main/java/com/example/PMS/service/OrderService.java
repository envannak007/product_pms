package com.example.PMS.service;

import com.example.PMS.dto.request.OrderRequest;
import com.example.PMS.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request);
    List<OrderResponse> findAll();
    OrderResponse findById(Integer id);
    OrderResponse update(Integer id,OrderRequest request);
    void delete(Integer id);
}
