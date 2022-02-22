package com.estia.ms.client.proxies;

import com.estia.ms.client.bean.CartBean;
import com.estia.ms.client.bean.CartItemBean;
import com.estia.ms.client.bean.OrdersBean;
import com.estia.ms.client.bean.ProductBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "ms-cart", url = "localhost:8092")
public interface MsCartProxy {
    @PostMapping(value = "/cart")
    public ResponseEntity<CartBean> createNewCart(@RequestBody CartBean cartData);

    @GetMapping(value = "/cart/{id}")
    public Optional<CartBean> getCart(@PathVariable Long id);

    @PostMapping(value = "/cart/{id}")
    public ResponseEntity<CartItemBean> addProductToCart(@PathVariable Long id, @RequestBody CartItemBean cartItem);

    @PutMapping(value = "/cart/{id}")
    public ResponseEntity<CartBean> updateCart(@PathVariable Long id, @RequestBody CartBean cart);

    @GetMapping(value = "/carts")
    public List<CartBean> list();
}
