package fr.utilix.eshop.api.exposition.controllers;

import fr.utilix.eshop.api.domain.services.ProductService;
import fr.utilix.eshop.api.exposition.dtos.request.ProductRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.ProductResponseDTO;
import fr.utilix.eshop.api.mappers.ProductMapper;
import fr.utilix.eshop.api.persistence.entities.CategoryEntity;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;
import fr.utilix.eshop.api.persistence.repositories.ProductRepository;
import jakarta.validation.Valid;
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
    private  final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> response  = productService.findAll();
        return ResponseEntity.ok(response);
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
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO response = productService.findById(id);
            return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO request
    ) {
        ProductResponseDTO response = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO request
    ){
       ProductResponseDTO response = productService.update(id, request);
       return ResponseEntity.ok(response);
    }

    /**
     *
     * @param id ide de produit pour mettre à jour
     * @param request meta data(les champs de produit) à mettre à jour
     * @return
     */
    private ResponseEntity<ProductResponseDTO> doUpdateProduct(Long id, ProductRequestDTO request) {

        ProductEntity existing = productRepository.findById(id).orElse(null);
        if(existing != null){
            existing.setName(request.name());
            existing.setDescription(request.description());
            existing.setImageUrl(request.imageUrl());
            existing.setActive(request.isActive());
            existing.setPrice(request.price());
            existing.setStock(request.stock());
            existing.setDiscount(request.discount());

            ProductEntity updated = productRepository.save(existing);
            return ResponseEntity.ok(ProductMapper.toDto(updated));
        }else {
            return ResponseEntity.notFound().build();
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllProducts(){
        productService.deleteAll();
        return ResponseEntity.noContent().build();
    }



}
