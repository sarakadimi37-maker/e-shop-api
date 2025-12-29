package fr.utilix.eshop.api.domain.services;

import fr.utilix.eshop.api.enumeration.OrderStatus;
import fr.utilix.eshop.api.exception.ResourceNotFoundException;
import fr.utilix.eshop.api.exposition.dtos.request.OrderItemRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.request.OrderRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.OrderResponseDTO;
import fr.utilix.eshop.api.mappers.OrderMapper;
import fr.utilix.eshop.api.persistence.entities.CustomerEntity;
import fr.utilix.eshop.api.persistence.entities.OrderEntity;
import fr.utilix.eshop.api.persistence.entities.OrderItemEntity;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;
import fr.utilix.eshop.api.persistence.repositories.OrderItemRepository;
import fr.utilix.eshop.api.persistence.repositories.OrderRepository;
import fr.utilix.eshop.api.persistence.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j // Lombok crée automatiquement une instance de `Logger` pour SLF4J (Simple Logging Facade for Java)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;


    public Page<OrderResponseDTO> findAll(Pageable pageable, Long customerId) {
        return orderRepository.findAllByCustomerId(pageable, customerId).map(OrderMapper::toDto);
    }


    public OrderResponseDTO findById(Long id){
       OrderEntity order = orderRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException
                       ("Order avec l'id" + id + "n'existe pas."));
       return OrderMapper.toDto(order);
    }

    // -------------attention appelaion de OrderRules avant de creation et update à faire
    @Transactional
    public void createAndUpdateOrder(Long customerId, OrderRequestDTO dto){
        // :vérifier si une command en cours (PENDING) exist pour custumer(id) -> OrderRepository;
        Optional<OrderEntity> order = orderRepository.findByCustomerAndStatus(customerId, OrderStatus.PENDING);
        if(order.isPresent()){
            // recupère les item de command et mettre à jour
            Optional<OrderItemEntity> existingOrderItem = orderItemRepository.getByOrderAndProduct(order.get().getId(), dto.item().productId());
            if(existingOrderItem.isPresent()){
                Integer upDateQt = existingOrderItem.get().getQuantity() + dto.item().quantity();
                if(upDateQt.equals(0)){
                    orderItemRepository.delete(existingOrderItem.get());
                }else{
                    existingOrderItem.get().setQuantity(upDateQt);
                    orderItemRepository.save(existingOrderItem.get());
                }

            }else{
                createOrderItem(dto, order.get());
            }

        }else{
            // Créer un nouvel order
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setStatus(OrderStatus.PENDING);
            CustomerEntity customer = new CustomerEntity();
            customer.setId(customerId);
            orderEntity.setCustomer(customer);
            orderRepository.save(orderEntity);
            log.debug("id order {}", orderEntity.getId());
            // créer un item
            createOrderItem(dto, orderEntity);
        }

    }

    private void createOrderItem(OrderRequestDTO dto, OrderEntity order) {
            Optional<ProductEntity> product = productRepository.findById(dto.item().productId());
            if (product.isPresent()) {
                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setUintPrice(product.get().getPrice());
                orderItem.setQuantity(dto.item().quantity());
                orderItem.setOrder(order);
                orderItem.setProduct(product.get());
                orderItemRepository.save(orderItem);
            }

    }

    public void delete(Long id){
        if(!orderRepository.existsById(id)){
            throw new ResourceNotFoundException("Impossible de supprimer : order " + id + " introuvable.");
        }
        orderRepository.deleteById(id);
    }


    public void deleteOrderItem(Long id) {
        if(!orderItemRepository.existsById(id)){
            throw new ResourceNotFoundException("Impossible de supprimer : orderItem " + id + " introuvable.");
        }
        orderItemRepository.deleteById(id);
    }
}
