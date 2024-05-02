package org.example.services;

import org.example.entities.Orders;
import org.example.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Orders getById(UUID id) {
        Optional<Orders> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElseGet(Orders::new);
    }


    public List<Orders> getAll() {
        return (List<Orders>) orderRepository.findAll();
    }

    @Transactional
    public Orders save(Orders orders) {
        return orderRepository.save(orders);
    }

    @Transactional
    public Orders delete(UUID id) {
        Orders orders = getById(id);
        if(orders.getId() != null)
            orderRepository.deleteById(id);
        return orders;
    }
}
