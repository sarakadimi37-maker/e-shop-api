package fr.utilix.eshop.api.exposition.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Description;


public record CustomerRequestDTO(

        @Valid
        @NotBlank(message = "Le champs prénom ne peut pas être vide")
        @Size(max = 50, min = 3, message = "Le prénom doit comporter au moins 3 caractères et au plus 50 caractères.")
        String firstName,

        @Valid
        @NotBlank(message = "Le champs prénom ne peut pas être vide")
        @Size(max = 50, min = 3, message = "Le nom doit comporter au moins 3 caractères et au plus 50 caractères.")
        String lastName,

        @Valid
        @NotBlank(message = "Le champs téléphone ne peut pas être vide")
        @Description("Le téléphone de l'utilisateur.")
        String phone


) {}
