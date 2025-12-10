package fr.utilix.eshop.api.exposition.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(

        @Valid
        @NotBlank(message = "Le label de category est obligatoire.")
        @NotNull(message = "Le category ne peut pas être null.")
        @Size(max = 100)
        String label
) {}
