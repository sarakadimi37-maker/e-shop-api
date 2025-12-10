package fr.utilix.eshop.api.mappers;

import fr.utilix.eshop.api.exposition.dtos.request.OrderItemRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.OrderItemResponseDTO;
import fr.utilix.eshop.api.exposition.dtos.response.ProductResponseDTO;
import fr.utilix.eshop.api.persistence.entities.OrderItemEntity;

public class OrderItemMapper {
    public static OrderItemResponseDTO toDto(OrderItemEntity entity) {
        Integer quantity = null;
        Double uintPrice = null;
        ProductResponseDTO product = null;
        quantity = entity.getQuantity();
        uintPrice = entity.getUintPrice();
        product = ProductMapper.toDto( entity.getProduct() );

        return new OrderItemResponseDTO(
                quantity,
                uintPrice,
                product
        );
    }

    public static OrderItemEntity toEntity(OrderItemRequestDTO dto){
        OrderItemEntity entity = new OrderItemEntity();
        entity.setQuantity(dto.quantity());

        // front donne id produit

        //1 chercher produit en bdd avec id
        Long productId = dto.productId();

       // ProductEntity productEntity = ProductMapper.toEntity(dto.product());
        return null;
    }


}
