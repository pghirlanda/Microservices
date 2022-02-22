package com.course.cart.controllers;

import com.course.cart.domain.Cart;
import com.course.cart.domain.CartItem;
import com.course.cart.repositories.CartItemRepository;
import com.course.cart.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
public class CartController {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @PostMapping(value = "/cart")
    public ResponseEntity<Cart> createNewCart(@RequestBody Cart cartData)
    {
        Cart cart = cartRepository.save(new Cart());
        if (cart == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new cart");
        return new ResponseEntity<Cart>(cart,HttpStatus.CREATED);
    }

    @GetMapping(value = "/cart/{id}")
    public Optional<Cart> getCart(@PathVariable Long id)
    {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coulnd't get cart");
        return cart;
    }

    @PostMapping(value = "/cart/{id}")
    @Transactional
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long id, @RequestBody CartItem cartItem)
    {
        Optional<Cart> cart = cartRepository.findById(id);
        if (!cart.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");
        cart.get().addProduct(cartItem);
        cartRepository.save(cart.get());
        return new ResponseEntity<CartItem>(cartItem, HttpStatus.CREATED);
    }

    @PutMapping(value = "/cart/{id}")
    @Transactional
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cart){
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (!optionalCart.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");
        }
        Cart cartInstance = optionalCart.get();
        cartInstance.setProducts(cart.getProducts());
        cartRepository.save(cartInstance);
        return new ResponseEntity<Cart>(cartInstance, HttpStatus.CREATED);
    }

    @GetMapping(value = "/carts")
    public List<Cart> list()
    {
        return cartRepository.findAll();
    }
}
