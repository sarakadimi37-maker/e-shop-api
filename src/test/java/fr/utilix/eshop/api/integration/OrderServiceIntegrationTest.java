package fr.utilix.eshop.api.integration;

import fr.utilix.eshop.api.domain.services.OrderService;
import fr.utilix.eshop.api.enumeration.OrderStatus;
import fr.utilix.eshop.api.exposition.dtos.request.OrderItemRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.request.OrderRequestDTO;
import fr.utilix.eshop.api.persistence.entities.CustomerEntity;
import fr.utilix.eshop.api.persistence.entities.OrderEntity;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;
import fr.utilix.eshop.api.persistence.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
@ActiveProfiles("integration")
@Sql("classpath:integration-data.sql")
class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private AddressRepository addressRepository;


    @Test
    @Transactional
    void shouldCreateOrderAndPersistItWithProducts() {
        // 1. Arrange : créer un client et un produit
        CustomerEntity entity = new CustomerEntity();
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setUser(userRepository.findById(1L).orElseThrow());
        entity.setAddress(addressRepository.findById(1L).orElseThrow());

        CustomerEntity customer = customerRepository.save(
                entity
        );

        ProductEntity product = productRepository.save(
                new ProductEntity(
                        "Potion", "Potion, Potion", "potion.png", true,
                        50.0, 10, 0.0, 3.0, null, null,null
                )
        );

        // 1. Arrange : Construire le DTO de commande
        OrderItemRequestDTO itemDTO = new OrderItemRequestDTO(2, product.getId());
        OrderRequestDTO dto = new OrderRequestDTO(OrderStatus.PENDING, itemDTO);

        // 2. Act
        orderService.createAndUpdateOrder(customer.getId(), dto);

        Optional<CustomerEntity> test = customerRepository.findById(customer.getId());
        // 3. Assert
        List<OrderEntity> all = orderRepository.findAll();
        assertThat(all).hasSize(1);

        OrderEntity persisted = all.getFirst();
        persisted.setCustomer(entity);

        assertThat(persisted.getCustomer().getFirstName()).isEqualTo("John");

    }

    @Test
    @Transactional
    void shouldDecreaseProductsStoreWhenOrderCreated(){
        // 1. Arrange : créer un customer
        CustomerEntity entity = new CustomerEntity();
        entity.setFirstName("John");
        entity.setLastName("Doe");


        CustomerEntity customer = customerRepository.save(
                entity
        );
        // 1. Arrange : créer un produit avec un stock de 10
        ProductEntity product = productRepository.save(
                new ProductEntity(
                        "Potion", "Potion, Potion", "potion.png", true,
                        50.0, 10, 0.0, 3.0, null, null,null
                )
        );
        // 1. Arrange : Construire le DTO de commande
        OrderItemRequestDTO itemDTO = new OrderItemRequestDTO(2, product.getId());
        OrderRequestDTO dto = new OrderRequestDTO(OrderStatus.PENDING, itemDTO);
        // 2. Act
        orderService.createAndUpdateOrder(customer.getId(), dto);
        // 3. Assert
        Optional<ProductEntity> product1 = productRepository.findById(product.getId());
        assertThat(product1.get().getStock()).isEqualTo(8);
    }
}