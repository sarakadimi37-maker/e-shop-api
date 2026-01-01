package fr.utilix.eshop.api.domain.rules;

import fr.utilix.eshop.api.exception.ValidationException;
import fr.utilix.eshop.api.exposition.dtos.request.ProductRequestDTO;
import fr.utilix.eshop.api.persistence.entities.ProductEntity;

import java.time.LocalDate;

public class ProductRules {

    private ProductRules(){}


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

    public static void validateDiscount(ProductRequestDTO product){

        if(product.discount() < 0 || product.discount() > 100){
            throw new ValidationException("La réduction (%) doit être comprise entre 0 et 100");
        }


        if(!product.isActive() && product.discount() > 0){
            throw new ValidationException("Un produit inactif ne peut pas avoir de promo");
        }

    }

    public static void negatifDiscount(ProductRequestDTO product){
        if(product.price() * (1 - product.discount() / 100) < 0){
            throw new ValidationException("Le prix remisé ne peut pas être négatif");
        }
    }

    public static void validateDiscountDate(ProductRequestDTO product){
        if(product.promoStart().isAfter( product.promoEnd())){
            throw new ValidationException("Les dates de promotion doivent être valides");
        }

        if(product.promoEnd().isBefore(LocalDate.now())){
            throw new ValidationException("Une promotion expirée ne peut plus être active");
        }

    }


}
