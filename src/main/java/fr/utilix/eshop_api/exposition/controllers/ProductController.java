package fr.utilix.eshop_api.exposition.controllers;

import fr.utilix.eshop_api.persistence.entities.ProductEntity;
import fr.utilix.eshop_api.persistence.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public List<ProductEntity> getAllProducts(){
        List<ProductEntity> products = productRepository.findAll();
        return products;
    }

    @GetMapping("/search")
    public List<ProductEntity> searchProducts(@RequestParam String keyword){
        List<ProductEntity> products = productRepository.findByNameContainingIgnoreCase(keyword);
        return products;
    }

}
