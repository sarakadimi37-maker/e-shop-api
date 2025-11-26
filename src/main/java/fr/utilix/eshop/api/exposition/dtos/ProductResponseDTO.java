package fr.utilix.eshop.api.exposition.dtos;

/**
 * Response DTO représente ce que le backend renvoie au front (ce qu’il répond).
 *  * Ici, on ne renvoie que les champs utiles à l’affichage.
 *  * Si un jour tu ajoutes un champ interne à ProductEntity par la suite,
 *  * tu pourras choisir de ne pas l’exposer dans le ResponseDTO.
 * @param id
 * @param name
 * @param description
 * @param imgUrl
 * @param price
 * @param stock
 * @param discount
 */
public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        String imgUrl,
        double price,
        int stock,
        double discount
) {
}
