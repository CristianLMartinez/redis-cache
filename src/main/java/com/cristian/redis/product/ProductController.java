package com.cristian.redis.product;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/product/{id}")
    @Cacheable(value = "product", key = "#id")
    public Product getProductById(@PathVariable long id) {
     return productRepository.findById(id).orElse(null);
    }

}
