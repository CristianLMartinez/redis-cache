package com.cristian.redis.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Transactional
    public Product save(Product product) {
        logger.info("Saving product: {}", product);
        return productRepository.save(product);
    }

    public Product getProduct(long id) {
        logger.info("Getting product from db: {}", id);
        return productRepository.findById(id)
                .orElseThrow();
    }

    public List<Product> getProducts() {
        logger.info("Getting products from db");
        return productRepository.findAll();
    }

    public String deleteProduct(long id) {
        logger.info("Deleting product from db: {}", id);
        productRepository.deleteById(id);
        return "Product deleted";
    }



}
