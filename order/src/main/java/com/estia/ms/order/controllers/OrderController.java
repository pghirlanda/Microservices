package com.estia.ms.order.controllers;

import com.estia.ms.order.domain.OrderItem;
import com.estia.ms.order.domain.Orders;
import com.estia.ms.order.repositories.OrderItemRepository;
import com.estia.ms.order.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PostMapping(value = "/orders")
    public ResponseEntity<Orders> createNewOrder(@RequestBody Orders orderData)
    {
        Orders order = ordersRepository.save(new Orders());
        if (order == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new order");
        return new ResponseEntity<Orders>(order, HttpStatus.CREATED);
    }

    @GetMapping(value = "/orders/{id}")
    public Optional<Orders> getOrder(@PathVariable Long id)
    {
        Optional<Orders> order = ordersRepository.findById(id);
        if (order == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coulnd't get order");
        return order;
    }

    @PostMapping(value = "/orders/{id}")
    @Transactional
    public ResponseEntity<Orders> addItemToOrder(@PathVariable Long id, @RequestBody OrderItem orderItem)
    {
        Optional<Orders> order = ordersRepository.findById(id);
        if (!order.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");
        order.get().addPaniers(orderItem);
        ordersRepository.save(order.get());
        return new ResponseEntity<Orders>(order.get(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/orders/{id}")
    @Transactional
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders order){
        Optional<Orders> optionalOrders = ordersRepository.findById(id);
        if (!optionalOrders.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");
        }
        Orders orderInstance = optionalOrders.get();
        orderInstance.setPaniers(order.getPaniers());
        orderInstance.setPrix(order.getPrix());
        ordersRepository.save(orderInstance);
        return new ResponseEntity<Orders>(orderInstance, HttpStatus.CREATED);
    }

    @GetMapping(value = "/orderss")
    public List<Orders> list()
    {
        return ordersRepository.findAll();
    }
}
