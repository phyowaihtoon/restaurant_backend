package creatip.restaurant.service;

import java.util.Set;

import creatip.restaurant.service.dto.MenuDTO;

public interface MenuService {

    MenuDTO getMenuForUser(Set<String> roleCodes);
    
}
