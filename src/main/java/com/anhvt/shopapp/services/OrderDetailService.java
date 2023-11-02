package com.anhvt.shopapp.services;

import com.anhvt.shopapp.dtos.OrderDetailDTO;
import com.anhvt.shopapp.exceptions.DataNotFoundException;
import com.anhvt.shopapp.models.Order;
import com.anhvt.shopapp.models.OrderDetail;
import com.anhvt.shopapp.models.Product;
import com.anhvt.shopapp.repositories.OrderDetailRepository;
import com.anhvt.shopapp.repositories.OrderRepository;
import com.anhvt.shopapp.repositories.ProductRepository;
import com.anhvt.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail) throws Exception {
        Order order = orderRepository.findById(newOrderDetail.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        Product product = productRepository.findById(newOrderDetail.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(newOrderDetail.getNumberOfProducts())
                .price(newOrderDetail.getPrice())
                .totalMoney(newOrderDetail.getTotalMoney())
                .color(newOrderDetail.getColor())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order detail not found!"));
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found!"));
        Order order = orderRepository.findById(orderDetail.getOrder().getId())
                .orElseThrow(() -> new DataNotFoundException("Order not found!"));
        Product product = productRepository.findById(orderDetail.getProduct().getId())
                .orElseThrow(() -> new DataNotFoundException("Product not found!"));
        orderDetail.setPrice(orderDetailDTO.getPrice());
        orderDetail.setColor(orderDetailDTO.getColor());
        orderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        orderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
