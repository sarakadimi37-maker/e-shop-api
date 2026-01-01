package fr.utilix.eshop.api.domain.rules;

import fr.utilix.eshop.api.enumeration.OrderStatus;
import fr.utilix.eshop.api.exception.ValidationException;
import fr.utilix.eshop.api.exposition.dtos.request.OrderItemRequestDTO;
import fr.utilix.eshop.api.persistence.entities.CustomerEntity;
import fr.utilix.eshop.api.persistence.entities.OrderItemEntity;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;

import java.util.List;

public class OrderRules {

    public static void validateCustomer(CustomerEntity customer){
        if(customer == null){
            throw new ValidationException("Le client est introuvable.");
        }

    }

    public static void validateProducts(List<ProductEntity> products){
        if (products == null || products.isEmpty()) {
            throw new ValidationException("Le panier est vide.");
        }
        boolean hasActiveProduct = products.stream()
                .anyMatch(ProductEntity::isActive);

        if (!hasActiveProduct) {
            throw new ValidationException("Le panier doit contenir au moins un produit actif.");
        }
    }

    public static void validateStock(OrderItemRequestDTO item, ProductEntity product){
        if (item.quantity() > product.getStock()) {
            throw new ValidationException(
                    "Stock insuffisant pour le produit : " + product.getName()
            );
        }
    }


    private static final double MAX_TOTAL = 5000;

    public static void validateTotal(double total){
        if (total <= 0) {
            throw new ValidationException("Le total doit être supérieur à 0.");
        }

        if (total > MAX_TOTAL) {
            throw new ValidationException(
                    "Le total ne doit pas dépasser un plafond de " + MAX_TOTAL + "€."
            );
        }
    }


    public static void validateOrderStatus(OrderStatus status){
      if(status == null){
          throw new ValidationException("Le statut ne peut pas être null.");
      }

    }


}
