package fr.utilix.eshop.api.exposition.dtos.response;

/**
 * Response DTO représente ce que le backend renvoie au front (ce qu’il répond).
 *  * Ici, on ne renvoie que les champs utiles à l’affichage.
 *  * Si un jour tu ajoutes un champ interne à ProductEntity par la suite,
 *  * tu pourras choisir de ne pas l’exposer dans le ResponseDTO.
 * @param id
 * @param name
 * @param description
 * @param imageUrl
 * @param price
 * @param quantity
 * @param inStock
 * @param discountPercentage
 * @param isPromo
 * @param isNew
 * @param category
 * @param rating
 */
public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        String imageUrl,
        double price,
        int quantity,
        boolean inStock,
        double discountPercentage,
        boolean isPromo,
        boolean isNew,
        String category,
        double rating
) {}
