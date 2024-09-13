package com.cristian.redis.product;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @PostConstruct
    public void init() {
        logger.info("Starting product controller");
    }

    @GetMapping("{id}")
    @Cacheable(value = "product", key = "#id")
    public Product getProductById(@PathVariable long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.save(product);
        URI location = URI.create("/product/" + savedProduct.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Cacheable(value = "products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @DeleteMapping("{id}")
    @CacheEvict(cacheNames = "product", allEntries = true)
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

}
