package com.example.PMS.orders.service.impl;

import com.example.PMS.orders.dto.request.OrderItemRequest;
import com.example.PMS.orders.dto.request.OrderRequest;
import com.example.PMS.orders.dto.response.OrderResponse;
import com.example.PMS.orders.entitty.OrderEntity;
import com.example.PMS.orders.entitty.OrderItemEntity;
import com.example.PMS.orders.repository.OrderItemRepository;
import com.example.PMS.orders.repository.OrderRepository;
import com.example.PMS.products.entity.ProductEntity;
import com.example.PMS.users.entitty.UserEntity;
import com.example.PMS.orders.enums.OrderStatus;
import com.example.PMS.common.exception.BadRequestException;
import com.example.PMS.common.exception.ResourceNotFoundException;
import com.example.PMS.orders.mapper.OrderMapper;
import com.example.PMS.products.repository.ProductRepository;
import com.example.PMS.notification.service.NotificationService;
import com.example.PMS.orders.service.OrderService;
import com.example.PMS.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public OrderResponse create(OrderRequest request) {
        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(()->new ResourceNotFoundException("User not found with id : " + request.userId()));

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        // save order
        orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.item()){
            ProductEntity product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(()->new ResourceNotFoundException("Product not found with id : " + itemRequest.productId()));
            Integer quantity = itemRequest.quantity();
            // check stock
            if (product.getStock() < quantity) {
                throw new BadRequestException("Not enough stock for : " + product.getName());
            }
            //Calculate subtotal
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            totalAmount = totalAmount.add(subtotal);

            // Delete stock
            product.setStock(product.getStock() - quantity);

            // Create order item
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(quantity);

            order.getOrderItem().add(orderItem);

            // save order item
            orderItemRepository.save(orderItem);
        }
        order.setTotalAmount(totalAmount);
        OrderEntity saved = orderRepository.save(order);

        // for send notification pel crated order success
        notificationService.notifyAdmins(
                "NEW_ORDER",
                "New Order",
                "New order #" + saved.getId() + "has been created",
                saved.getId()
        );

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
                .orElseThrow(()->new ResourceNotFoundException("Order not found with id : " + id));
        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse update(Integer id, OrderRequest request) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Order not found with id : " + id));

        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(()->new ResourceNotFoundException("User not found with id : " + request.userId()));

        order.setUser(user);

        // Get oid order item
        List<OrderItemEntity> oldItems = orderItemRepository.findByOrderId(id);

        // return old stock
        for (OrderItemEntity oldItem : oldItems){
            ProductEntity product = oldItem.getProduct();
            product.setStock(product.getStock() +  oldItem.getQuantity());
            productRepository.save(product);
        }

        // delete old item
        orderItemRepository.deleteAll(oldItems);

        // calculate new total
        BigDecimal totalAmount = BigDecimal.ZERO;

        // Create new order item
        for (OrderItemRequest itemRequest : request.item()){
            ProductEntity product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(()->new ResourceNotFoundException("Product not found with id : " + itemRequest.productId()));
            Integer quantity = itemRequest.quantity();

            // check stock
            if (product.getStock() < quantity){
                throw new BadRequestException("Not enough stock for : " + product.getName());
            }

            // calculate subtotal
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            totalAmount = totalAmount.add(subtotal);

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            // create new order item
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());

            orderItemRepository.save(orderItem);
        }
        // update order
        order.setTotalAmount(totalAmount);

        // save order
        OrderEntity update = orderRepository.save(order);

        return orderMapper.toResponse(update);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Order not found with id : " + id));

        List<OrderItemEntity> orderItems = orderItemRepository.findByOrderId(id);

        for (OrderItemEntity orderItem : orderItems){
            ProductEntity product = orderItem.getProduct();
            product.setStock(product.getStock() + orderItem.getQuantity());
            productRepository.save(product);
        }

        // delete order item
        orderItemRepository.deleteAll(orderItems);

        // delete order
        orderRepository.delete(order);

    }
}
