package fr.utilix.eshop.api.exposition.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressRequestDTO(

        @Valid
        @NotBlank(message = "Le nom de rue/boulvard/avenue est obligatoire.")
        @NotNull(message = "Le nom de rue/boulvard/avenue ne paut pas être null.")
        @Size(max = 120)
        String street,

        @Valid
        @NotBlank(message = "Le ville est obligatoire.")
        @NotNull(message = "Le ville ne peut pas être null.")
        @Size(max = 80)
        String city,

        @Valid
        @NotBlank(message = "Le code postale est obligatoire.")
        @NotNull(message = "Le code postale ne peut pas être null.")
        @Size(max = 10)
        String zipCode,

        @Valid
        @NotBlank(message = "Le pays est obligatoire.")
        @NotNull(message = "Le pays ne peut pas être null.")
        @Size(max = 80)
        String country


) {

}
