package fr.utilix.eshop.api.exposition.dtos.response;
import fr.utilix.eshop.api.enumeration.OrderStatus;

import java.util.List;

public record OrderResponseDTO(
         OrderStatus status,
         List<OrderItemResponseDTO> orderItems
) {}
