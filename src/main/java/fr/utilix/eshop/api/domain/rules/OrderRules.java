package fr.utilix.eshop.api.domain.rules;

import fr.utilix.eshop.api.enumeration.OrderStatus;
import fr.utilix.eshop.api.exception.ValidationException;
import fr.utilix.eshop.api.persistence.entities.CustomerEntity;
import fr.utilix.eshop.api.persistence.entities.OrderItemEntity;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;

import java.util.List;

public class OrderRules {
    /**
     * - validateCustomer() : le client doit exister et ne pas avoir un compte suspendu
     * - validateProducts() : le panier doit contenir au moins un produit actif
     * - validateStock() : le stock doit être suffisant pour chaque produit
     * - validateTotal() : le total ne doit pas dépasser un plafond
     * - validateOrderStatus() : Le  statut doit être valide : PENDING, SHIPPED, DELIVERED, CANCELLED).
     */
    public static void validateCustomer(CustomerEntity customer){
        if(customer == null){
            throw new ValidationException("Le client est introuvable.");
        }
        /*
        if (customer.isSuspended()) {
            throw new ValidationException("Le client a un compte suspendu et ne peut pas passer de commande.");
        }

         */

    }

    public static void validateProducts(List<OrderItemEntity> items){
        boolean hasActiveProduct = items.stream()
                .anyMatch(item -> item.getProduct().isActive());

        if (!hasActiveProduct) {
            throw new ValidationException("Le panier doit contenir au moins un produit actif.");
        }
    }

    public static void validateStock(ProductEntity product, int quantity){
        if (product.getStock() < quantity) {
            throw new ValidationException(
                    "Stock insuffisant pour le produit : " + product.getName()
            );
        }
    }


    private static final double MAX_TOTAL = 500000;

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

      boolean isValid =
              status == OrderStatus.PENDING ||
                      status == OrderStatus.SHIPPED ||
                      status == OrderStatus.DELIVERED ||
                      status == OrderStatus.CANCELLED;
      if(!isValid){
          throw new ValidationException(
                  "Le statut doit être valide : PENDING, SHIPPED, DELIVERED, CANCELLED"
          );
      }
    }

}
