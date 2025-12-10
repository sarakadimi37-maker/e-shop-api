package fr.utilix.eshop.api.mappers;

import fr.utilix.eshop.api.exposition.dtos.request.FavoriteRequestDTO;
import fr.utilix.eshop.api.exposition.dtos.response.FavoriteResponseDTO;
import fr.utilix.eshop.api.persistence.entities.FavoriteEntity;

public class FavoriteMapper {

    public static FavoriteEntity toEntity(FavoriteRequestDTO dto){
        FavoriteEntity entity = new FavoriteEntity();
        // todo
        return entity;
    }


    public static FavoriteResponseDTO toDto(FavoriteEntity entity){
        return null;
    }


}
