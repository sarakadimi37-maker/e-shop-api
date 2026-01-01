package fr.utilix.eshop.api.unit;

import fr.utilix.eshop.api.domain.rules.ProductRules;
import fr.utilix.eshop.api.exception.ValidationException;
import fr.utilix.eshop.api.exposition.dtos.request.ProductRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRulesTest {

    private ProductRequestDTO product;

    @BeforeEach
    void setup() {
        // intialiser pour l'enseble des tests


        LocalDate startDate = LocalDate.parse("2025-05-12");
        LocalDate endDate = LocalDate.parse("2025-01-12");
        product = new ProductRequestDTO(
                "Pomme",
                "descripotionnnn",
                "https://placehold.co/300x200/FF6347/ffffff?text=Nike",
                true,
                99.9,
                5,
                20,
                startDate,
                endDate

        );
    }

    @Test
    @DisplayName("Should throw if discount abouve 100")
    void shouldThrowIfDiscountAbove100() {
        // Arrange
        product = product.toBuilder().discount(110).build();
        // Act
        Exception ex = assertThrows(ValidationException.class,
                () -> ProductRules.validateDiscount(product));
        // Assert
        assertTrue(ex.getMessage().contains("La réduction (%) doit être comprise entre 0 et 100"));
    }

    @Test
    @DisplayName("Should throw if discount negative")
    void shouldThrowIfDiscountNegative() {
        // Arrange
        product = product.toBuilder().price(-10).build();
        // Act
        Exception ex = assertThrows(ValidationException.class,
                () -> ProductRules.negatifDiscount(product));
        // Assert
        assertEquals("Le prix remisé ne peut pas être négatif", ex.getMessage());
    }


    @Test
    @DisplayName("Should throw if inactive product has discount")
    void shouldThrowIfInactiveProductHasDiscount() {
        // Arrange
        product = product.toBuilder().isActive(false).build();
        // Act
        Exception ex = assertThrows(ValidationException.class,
                () -> ProductRules.validateDiscount(product));
        // Assert
        assertEquals("Un produit inactif ne peut pas avoir de promo", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw if promotion date is invalid")
    void shouldThrowIfPromoDateInvalid() {

        // Arrange
        LocalDate startDate = LocalDate.parse("2025-01-12");
        product = product.toBuilder().promoEnd(startDate).build();
        // Act
        Exception ex = assertThrows(ValidationException.class,
                () -> ProductRules.validateDiscountDate(product));
        // Assert
        assertTrue(ex.getMessage().contains("Les dates de promotion doivent être valides"));
    }

    @Test
    @DisplayName("Should throw if promotion is expired")
    void shouldThrowIfPromoExpired() {
        // Arrange
        LocalDate endDate = LocalDate.parse("2025-05-20");
        product = product.toBuilder().promoEnd(endDate).build();

        // Act
        Exception ex = assertThrows(ValidationException.class,
                () -> ProductRules.validateDiscountDate(product));
        // Assert
        assertTrue(ex.getMessage().contains("Une promotion expirée ne peut plus être active"));
    }

}