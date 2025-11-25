package fr.utilix.eshop_api.exposition.controllers;

import fr.utilix.eshop_api.persistence.entities.CategoryEntity;
import fr.utilix.eshop_api.persistence.entities.ProductEntity;
import fr.utilix.eshop_api.persistence.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts(){
        List<ProductEntity> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public List<ProductEntity> searchProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false,defaultValue = "1000") double maxPrice,
            @RequestParam(defaultValue = "asc") String order){
        // Du code Java qui s'occupe de filtrer une collection selon les critères
        List<ProductEntity> products = productRepository.findAll();
        if(category != null){
          return  products.stream().filter(product -> {
                List<CategoryEntity> categories = product.getCategories();
                return categories.stream().anyMatch(cat -> cat.getLabel().equals(category));
            }).toList();
        }
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) {
        ProductEntity product = productRepository.findById(id).orElse(null);
        if (product != null){
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product){
        ProductEntity saved = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductEntity newData
        ){
        ProductEntity existing = productRepository.findById(id).orElse(null);
        if(existing != null){
            existing.setName(newData.getName());
            existing.setDescription(newData.getDescription());
            existing.setImageUrl(newData.getImageUrl());
            existing.setActive(newData.isActive());
            existing.setPrice(newData.getPrice());
            existing.setStock(newData.getStock());
            existing.setDiscount(newData.getDiscount());

            ProductEntity updated = productRepository.save(existing);
            return ResponseEntity.ok(updated);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



}
