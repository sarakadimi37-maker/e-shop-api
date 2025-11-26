package fr.utilix.eshop.api.mappers;
import fr.utilix.eshop.api.exposition.dtos.ProductRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.ProductResponseDTO;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    public ProductEntity toEntity(ProductRequestDTO dto);

    public ProductResponseDTO toDto(ProductEntity entity);
}
