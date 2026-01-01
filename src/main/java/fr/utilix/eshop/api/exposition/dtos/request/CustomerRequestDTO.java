package fr.utilix.eshop.api.exposition.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Description;

/**
 * Ce DTO représente ce que le client envoie (lors d’un POST ou PUT).
 * on met les champs qu'on a besoin
 * @param firstName
 * @param lastName
 * @param addressId
 */
public record CustomerRequestDTO(
        @Valid
        Long userId,
        @Valid
        @NotBlank(message = "Le champs prénom ne peut pas être vide")
        @Size(max = 50, min = 3, message = "Le prénom doit comporter au moins 3 caractères et au plus 50 caractères.")
        String firstName,

        @Valid
        @NotBlank(message = "Le champs prénom ne peut pas être vide")
        @Size(max = 50, min = 3, message = "Le nom doit comporter au moins 3 caractères et au plus 50 caractères.")
        String lastName,

        @Description("L'adresse de l'utilisateur.")
        Long addressId


) {}
