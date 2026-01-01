package fr.utilix.eshop.api.exposition.controllers;

import fr.utilix.eshop.api.domain.services.ProductService;
import fr.utilix.eshop.api.exposition.PagenateUtils;
import fr.utilix.eshop.api.exposition.dtos.request.ProductRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.ProductResponseDTO;
import fr.utilix.eshop.api.mappers.ProductMapper;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;
import fr.utilix.eshop.api.persistence.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private  final ProductService productService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        String sortField = sort[0];
        String sortDirection = sort.length > 1 ? sort[1] : "asc";

        Sort sortDirction = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(
                page,
                size,
                sortDirction
        );

        Page<ProductResponseDTO> productPage = productService.getAllProducts(pageable);

        // 👇 On prépare et renvoie la réponse au client, en incluant les résultats (bien sûr), mais aussi "où en est le client dans sa recherche" : page actuelle, nombre d'élements par page, nombre de pages total.
        Map<String, Object> response = PagenateUtils.getResponse(productPage);

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
          return  products.stream().filter(product -> product.getCategorie().getLabel().equals(category)).toList();
        }
        return products;
    }

    @Operation(
            summary = "Obtenir un produit par son ID",
            description = "Retourne les informations détaillées d’un produit existant"
    )
    @ApiResponse(responseCode = "200", description = "Produit trouvé avec succès")
    @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO response = productService.findById(id);
            return ResponseEntity.ok(response);
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

    @GetMapping("/discount")
    public ResponseEntity<List<ProductResponseDTO>> getDiscount( @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(
                page,
                size
        );
        List<ProductResponseDTO>  products = productService.getDiscouts(pageable);
        return ResponseEntity.ok(products);
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
