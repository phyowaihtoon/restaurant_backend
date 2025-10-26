package creatip.restaurant.service.mapper;


import creatip.restaurant.domain.ActionItem;
import creatip.restaurant.service.dto.ActionItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionItemMapper extends EntityMapper<ActionItemDTO, ActionItem> {

}
