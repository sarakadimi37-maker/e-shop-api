package fr.utilix.eshop.api.exposition;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class PagenateUtils {

    private PagenateUtils(){}

    public static Map<String, Object> getResponse(Page<?> pageDto){
        Map<String, Object> response = new HashMap<>();
        response.put("content", pageDto.getContent());
        response.put("currentPage", pageDto.getNumber());
        response.put("totalItems", pageDto.getTotalElements());
        response.put("totalPages", pageDto.getTotalPages());

        return response;
    }
}
