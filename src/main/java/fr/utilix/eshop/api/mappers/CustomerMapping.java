package fr.utilix.eshop.api.mappers;

import fr.utilix.eshop.api.exposition.dtos.request.AddressRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.request.CustomerRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.*;
import fr.utilix.eshop.api.persistence.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerMapping {

    @Mapping(source = "street", target ="street" )
    AddressEntity toAddressEntity(AddressRequestDTO dto);

    @Mapping(source = "firstName", target ="firstName" )
    CustomerEntity toEntity(CustomerRequestDTO dto);

    // *********************************

    @Mapping(source = "street", target ="street" )
    AddressResponseDTO toAddressDto(AddressEntity entity);

    @Mapping(source = "name", target ="name" )
    ProductResponseDTO toProductDto(ProductEntity entity);

    @Mapping(source = "quantity", target ="quantity" )
    OrderItemResponseDTO toOrderItemDto(OrderItemEntity entity);

    @Mapping(source = "status", target ="status" )
    OrderResponseDTO toOrderDto(OrderEntity entity);

    @Mapping(source = "id", target ="id" )
    FavoriteResponseDTO toFavoriteDto(FavoriteEntity entity);


    @Mapping(source = "firstName", target ="firstName" )
    CustomerResponseDTO toDto(CustomerEntity entity);


}
