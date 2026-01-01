package fr.utilix.eshop.api.unit;

import fr.utilix.eshop.api.domain.rules.OrderRules;
import fr.utilix.eshop.api.enumeration.OrderStatus;
import fr.utilix.eshop.api.exception.ValidationException;
import fr.utilix.eshop.api.exposition.dtos.request.OrderItemRequestDTO;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRulesTest {

    private ProductEntity activeProduct;
    private ProductEntity inactiveProduct;
    private OrderItemRequestDTO validItem;
    private OrderItemRequestDTO tooLargeItem;

    @BeforeEach
    void setup() {
        // Arrange
        activeProduct = new ProductEntity();
        activeProduct.setId(1L);
        activeProduct.setName("Magic Potion");
        activeProduct.setDescription("Magic Potion,Magic Potion,Magic Potion");
        activeProduct.setImageUrl("https://placehold.co/300x200/FF6347/ffffff?text=Nike");
        activeProduct.setPrice(100.0);
        activeProduct.setStock(10);
        activeProduct.setActive(true);
        activeProduct.setDiscount(20.0);
        activeProduct.setRating(4.5);

        inactiveProduct = new ProductEntity();
        inactiveProduct.setId(111L);
        inactiveProduct.setName("Old Potion");
        inactiveProduct.setDescription("bla bla car");
        inactiveProduct.setImageUrl("https://placehold.co/300x200/FF6347/ffffff?text=Nike");
        inactiveProduct.setPrice(50.0);
        inactiveProduct.setStock(5);
        inactiveProduct.setActive(false);


        validItem = new OrderItemRequestDTO(3, 1L);
        tooLargeItem = new OrderItemRequestDTO(15, 1L);
    }

    @Test
    @DisplayName("Should throw if all products are inactive")
    void shouldThrowIfAllProductsInactive(){
        List<ProductEntity> products = List.of(inactiveProduct);

        Exception ex = assertThrows(ValidationException.class,
                () -> OrderRules.validateProducts(products));
        assertTrue(ex.getMessage().contains("Le panier doit contenir au moins un produit actif."));
    }

    @Test
    @DisplayName("Should pass if at least one product is active")
    void shouldPassIfAtLeastOneProductIsActive(){
        List<ProductEntity> products = List.of(activeProduct, inactiveProduct);
        assertDoesNotThrow(() -> OrderRules.validateProducts(products));
    }

    @Test
    @DisplayName("Should throw if ordered quantity exceeds stock")
    void shouldThrowIfQuantityExceedsStock() {
        Exception ex = assertThrows(ValidationException.class,
                () -> OrderRules.validateStock(tooLargeItem, activeProduct));

        assertTrue(ex.getMessage().contains("Stock insuffisant"));
    }

    @Test
    @DisplayName("Should pass if ordered quantity is within stock")
    void shouldPassIfQuantityWithinStock() {
        assertDoesNotThrow(() -> OrderRules.validateStock(validItem, activeProduct));
    }

    @Test
    @DisplayName("Should throw if total exceeds allowed limit")
    void shouldThrowIfTotalExceedsLimit() {
        double total = 6000.0;

        Exception ex = assertThrows(ValidationException.class,
                () -> OrderRules.validateTotal(total));

        assertTrue(ex.getMessage().contains("plafond"));
    }

    @Test
    @DisplayName("Should pass if total is under allowed limit")
    void shouldPassIfTotalUnderLimit() {
        double total = 4999.99;

        assertDoesNotThrow(() -> OrderRules.validateTotal(total));
    }

    @Test
    void shouldThrowIfStatusNull() {
        assertThrows(
                ValidationException.class,
                () -> OrderRules.validateOrderStatus(null)
        );
    }

    @Test
    @DisplayName("Should pass if order status is valid")
    void shouldPassIfStatusValid() {
        OrderStatus status = OrderStatus.SHIPPED;

        assertDoesNotThrow(() -> OrderRules.validateOrderStatus(status));
    }


}
