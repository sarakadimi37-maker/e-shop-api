package fr.utilix.eshop.api.domain.services;

import fr.utilix.eshop.api.exception.ResourceNotFoundException;
import fr.utilix.eshop.api.exception.ValidationException;
import fr.utilix.eshop.api.exposition.dtos.request.FavoriteRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.FavoriteResponseDTO;
import fr.utilix.eshop.api.mappers.FavoriteMapper;
import fr.utilix.eshop.api.persistence.entities.CustomerEntity;
import fr.utilix.eshop.api.persistence.entities.FavoriteEntity;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;
import fr.utilix.eshop.api.persistence.repositories.CustomerRepository;
import fr.utilix.eshop.api.persistence.repositories.FavoriteRepository;
import fr.utilix.eshop.api.persistence.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public List<FavoriteResponseDTO> findAllFavorite(Long customerId){
        return favoriteRepository.findByCustomerId(customerId).stream().map(FavoriteMapper::toDto).toList();

    }

    @Transactional
    public FavoriteResponseDTO create(FavoriteRequestDTO dto)throws ValidationException {

        FavoriteEntity entity = new FavoriteEntity();
        ProductEntity product = productRepository.findById(dto.productId()).orElseThrow();

        CustomerEntity customer = customerRepository.findById(dto.customerId()).orElseThrow();
        entity.setProduct(product);
        entity.setCustomer(customer);
        FavoriteEntity saved = favoriteRepository.save(entity);
        return FavoriteMapper.toDto(saved);
    }

    @Transactional
    public int delete(Long customerId, Long productId) {

        int deleted = favoriteRepository.deleteByCustomerIdAndProductId(customerId, productId);
        if(deleted == 0){
            throw new ResourceNotFoundException("Impossible de supprimer : favorite " + customerId + "et " + productId +" introuvable.");
        }
        return deleted;
    }
}
