package com.estia.ms.client.proxies;

import com.estia.ms.client.bean.CartBean;
import com.estia.ms.client.bean.CartItemBean;
import com.estia.ms.client.bean.OrderItemBean;
import com.estia.ms.client.bean.OrdersBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "ms-orders", url = "localhost:8093")
public interface MsOrdersProxy {
    @PostMapping(value = "/orders")
    public ResponseEntity<OrdersBean> createNewOrder(@RequestBody OrdersBean orderData);

    @GetMapping(value = "/orders/{id}")
    public Optional<OrdersBean> getOrder(@PathVariable Long id);

    @PostMapping(value = "/orders/{id}")
    public ResponseEntity<OrdersBean> addItemToOrder(@PathVariable Long id, @RequestBody OrderItemBean orderItem);

    @PutMapping(value = "/orders/{id}")
    public ResponseEntity<OrdersBean> updateOrder(@PathVariable Long id, @RequestBody OrdersBean order);

    @GetMapping(value = "/orderss")
    public List<OrdersBean> list();
}
