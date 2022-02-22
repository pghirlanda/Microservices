package com.estia.ms.product.controllers;

import com.estia.ms.product.domain.Product;
import com.estia.ms.product.repositories.ProductRepository;
import net.bytebuddy.dynamic.DynamicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = "/products")
    public List<Product> list()
    {
        return productRepository.findAll();
    }

    @GetMapping(value = "/product/{id}")
    public Optional<Product> get(@PathVariable Long id)
    {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specified product doesn't exist");
        return product;
    }
}
