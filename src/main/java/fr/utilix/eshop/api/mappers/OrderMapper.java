package fr.utilix.eshop.api.mappers;

import fr.utilix.eshop.api.exposition.dtos.response.OrderItemResponseDTO;
import fr.utilix.eshop.api.exposition.dtos.request.OrderRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.OrderResponseDTO;
import fr.utilix.eshop.api.persistence.entities.OrderEntity;
import fr.utilix.eshop.api.persistence.entities.OrderItemEntity;
import fr.utilix.eshop.api.enumeration.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static OrderEntity toEntity(OrderRequestDTO dto){
        OrderEntity entity = new OrderEntity();
        entity.setStatus(dto.status());
        //entity.setOrderItems();
        return entity;
    }


    public static OrderResponseDTO toDto(OrderEntity entity) {
        OrderStatus status = null;

        List<OrderItemResponseDTO> orderItems = new ArrayList<>();
        List<OrderItemEntity> orderItemEntity = entity.getOrderItems();
        for(OrderItemEntity item : orderItemEntity){
            OrderItemResponseDTO dto = OrderItemMapper.toDto(item);
            orderItems.add(dto);
        }

        status = entity.getStatus();

        orderItems = orderItemEntitySetToOrderItemResponseDTOList( entity.getOrderItems() );
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO( status, orderItems );
        return orderResponseDTO;
    }


    protected static List<OrderItemResponseDTO> orderItemEntitySetToOrderItemResponseDTOList(List<OrderItemEntity> set) {
        if ( set == null ) {
            return null;
        }

        List<OrderItemResponseDTO> list = new ArrayList<OrderItemResponseDTO>( set.size() );
        for ( OrderItemEntity orderItemEntity : set ) {
            list.add( toOrderItemDto( orderItemEntity ) );
        }

        return list;
    }

    private static OrderItemResponseDTO toOrderItemDto(OrderItemEntity orderItemEntity) {
        return new OrderItemResponseDTO(1, 1d, null);
    }


}
