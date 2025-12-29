package fr.utilix.eshop.api.mappers;

import fr.utilix.eshop.api.exposition.dtos.response.FavoriteResponseDTO;
import fr.utilix.eshop.api.persistence.entities.FavoriteEntity;

public class FavoriteMapper {

    private FavoriteMapper(){}
    public static FavoriteResponseDTO toDto(FavoriteEntity entity){
        return new FavoriteResponseDTO(
                entity.getId(),
                ProductMapper.toDto(entity.getProduct())
        );
    }


}
