package com.example.PMS.service.ipml;

import com.example.PMS.dto.request.OrderItemRequest;
import com.example.PMS.dto.response.OrderItemResponse;
import com.example.PMS.entity.OrderEntity;
import com.example.PMS.entity.OrderItems;
import com.example.PMS.entity.ProductEntity;
import com.example.PMS.exception.ResourceNotFoundException;
import com.example.PMS.mapper.OrderItemMapper;
import com.example.PMS.repository.OrderItemRepository;
import com.example.PMS.repository.OrderRepository;
import com.example.PMS.repository.ProductRepository;
import com.example.PMS.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderItemResponse create(OrderItemRequest request) {

        OrderEntity order = orderRepository.findById(request.orderId())
                .orElseThrow(()->new ResourceNotFoundException("Order not found with id : "+ request.orderId()));

        ProductEntity product = productRepository.findById(request.productId())
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id :"+ request.productId()));

        OrderItems orderItems = new OrderItems();
        orderItems.setOrder(order);
        orderItems.setProduct(product);
        orderItems.setQuantity(request.quantity());
        orderItems.setPrice(request.price());

        OrderItems saved = orderItemRepository.save(orderItems);
        return orderItemMapper.toResponse(saved);
    }

    @Override
    public List<OrderItemResponse> findAll() {
        return orderItemRepository.findAll()
                .stream()
                .map(orderItemMapper::toResponse)
                .toList();
    }

    @Override
    public OrderItemResponse findById(Integer id) {
        OrderItems orderItems = orderItemRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Order items not found with id : "+ id));
        return orderItemMapper.toResponse(orderItems);
    }

    @Override
    public OrderItemResponse update(Integer id, OrderItemRequest request) {

        OrderItems orderItems = orderItemRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Order items not found with id : "+ id));

        OrderEntity order = orderRepository.findById(request.orderId())
                .orElseThrow(()->new ResourceNotFoundException("Order not found with id : "+ request.orderId()));

        ProductEntity product = productRepository.findById(request.productId())
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id :"+ request.productId()));

        orderItems.setOrder(order);
        orderItems.setProduct(product);
        orderItems.setQuantity(request.quantity());
        orderItems.setPrice(request.price());

        OrderItems updated = orderItemRepository.save(orderItems);
        return orderItemMapper.toResponse(updated);
    }

    @Override
    public void delete(Integer id) {
        OrderItems orderItems = orderItemRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Order items not found with id : "+ id));
        orderItemRepository.delete(orderItems);

    }
}
