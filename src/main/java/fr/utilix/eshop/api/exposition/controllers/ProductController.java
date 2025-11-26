package fr.utilix.eshop.api.exposition.controllers;

import fr.utilix.eshop.api.exception.ResourceNotFoundException;
import fr.utilix.eshop.api.exposition.dtos.ProductRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.ProductResponseDTO;
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
import java.util.Optional;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> dtos = productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
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
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Produit avec l'ID : " + id + " n'existe pas."
                ));
            ProductResponseDTO dto = productMapper.toDto(product);
            return ResponseEntity.ok(dto);

    }

    /**
     *  La version ameliorer syntaxiquement
     * @param
     * @return
     */
    /*
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getwProductById(@PathVariable Long id){
        return productRepository.findById(id)
                .map(ProductMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }
   */

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO request
    ) {
        ProductEntity entity = productMapper.toEntity(request);
        ProductEntity saved = productRepository.save(entity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO request
    ){
       return doUpdateProduct(id, request);
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
            return ResponseEntity.ok(productMapper.toDto(updated));
        }else {
            return ResponseEntity.notFound().build();
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllProducts(){
        productRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }



}
