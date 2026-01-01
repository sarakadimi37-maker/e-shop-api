package fr.utilix.eshop.api.domain.services;

import fr.utilix.eshop.api.domain.rules.ProductRules;
import fr.utilix.eshop.api.exception.ResourceNotFoundException;
import fr.utilix.eshop.api.exception.ValidationException;
import fr.utilix.eshop.api.exposition.dtos.request.ProductRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.ProductResponseDTO;
import fr.utilix.eshop.api.mappers.ProductMapper;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;
import fr.utilix.eshop.api.persistence.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // Exemple : Invalide le cache quand on ajoute un produit
    @CacheEvict(value = "products", allEntries = true)
    public ProductEntity addProduct(ProductEntity product) {
        return productRepository.save(product);
    }


    public List<ProductResponseDTO> findAll(){

        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    public ProductResponseDTO findById(Long id){
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Produit avec l'id " + id + " n'existe pas."
                ));
        return ProductMapper.toDto(product);
    }

    /**
     * On applique les règles métiers de Product + mapping
     * @param dto
     * @return
     */
    public ProductResponseDTO create(ProductRequestDTO dto) throws ValidationException {
        ProductEntity entity = ProductMapper.toEntity(dto);
        ProductRules.validateBeforeCreation(entity);
        ProductEntity saved = productRepository.save(entity);
        return ProductMapper.toDto(saved);

    }

    /**
     * on délègue à l'entité d'encapsuler sa logique de mise à jour + on applique les règles métier de Product + mapping
     * @param id
     * @param dto
     * @return
     */
    public  ProductResponseDTO update(Long id, ProductRequestDTO dto){
        ProductEntity existing = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Produit " + id + " introuvable."));
        existing.updateFrom(dto);
        ProductRules.validateBeforeUpdate(existing);
        ProductEntity saved = productRepository.save(existing);
        return ProductMapper.toDto(saved);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void delete(Long id){
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Impossible de supprimer : Produit " + id + " introuvable.");
        }
        productRepository.deleteById(id);
    }

    public List<ProductResponseDTO> getDiscouts(Pageable pageable){
        return productRepository.findPromotion(pageable).stream().map(ProductMapper::toDto).toList();
    }

    public void deleteAll() {
        // productRepository.deleteAll();
        // Todo les regle metier concernant de suppression d'un produit
    }

    @Cacheable("Products")
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        return productRepository
                .findAll(pageable)
                .map(ProductMapper::toDto);
    }
}
