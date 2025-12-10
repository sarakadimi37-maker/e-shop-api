package fr.utilix.eshop.api.domain.rules;

import fr.utilix.eshop.api.exception.ValidationException;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;

public class ProductRules {

    public static void validateBeforeCreation(ProductEntity product) {
        if (product.getPrice() <= 0) {
            System.out.println("p -> : " + product.getPrice());
            throw new ValidationException("Le prix doit être supérieur à 0.");
        }
        if (product.getStock() < 0) {
            throw new ValidationException("Le stock ne peut pas être négatif.");
        }
    }

public static void validateBeforeUpdate(ProductEntity product){
        if (product.getPrice() > 10000) {
            throw new ValidationException("Le prix dépasee la limite autorisée.");
        }
    }


}
