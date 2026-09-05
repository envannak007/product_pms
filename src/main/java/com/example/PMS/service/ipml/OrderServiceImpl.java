package com.example.PMS.service.ipml;

import com.example.PMS.dto.request.OrderRequest;
import com.example.PMS.dto.response.OrderResponse;
import com.example.PMS.entity.OrderEntity;
import com.example.PMS.entity.UserEntity;
import com.example.PMS.exception.ResourceNotFoundException;
import com.example.PMS.mapper.OrderMapper;
import com.example.PMS.repository.OrderRepository;
import com.example.PMS.repository.UserRepository;
import com.example.PMS.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse create(OrderRequest request) {
        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(()->new ResourceNotFoundException("User not found with id : " + request.userId()));
        OrderEntity order = new OrderEntity();
        order.setTotalAmount(request.totalAmount());
        order.setStatus(request.status());
        order.setUser(user);

        OrderEntity saved = orderRepository.save(order);
        return orderMapper.toResponse(saved);
    }

    @Override
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse findById(Integer id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Order not found with id : "+ id));

        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse update(Integer id, OrderRequest request) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Order not found with id : "+ id));

        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(()->new ResourceNotFoundException("User not found with id : " + request.userId()));

        order.setTotalAmount(request.totalAmount());
        order.setStatus(request.status());
        order.setUser(user);

        OrderEntity update = orderRepository.save(order);

        return orderMapper.toResponse(update);
    }

    @Override
    public void delete(Integer id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Order not found with id : "+id));
        orderRepository.delete(order);
    }
}
