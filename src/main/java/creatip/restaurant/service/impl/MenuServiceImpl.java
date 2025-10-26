package creatip.restaurant.service.impl;

import creatip.restaurant.domain.ActionItem;
import creatip.restaurant.domain.ActionItemOrder;
import creatip.restaurant.domain.ActionRole;
import creatip.restaurant.repository.ActionItemOrderRepository;
import creatip.restaurant.repository.ActionItemRepository;
import creatip.restaurant.repository.ActionRoleRepository;
import creatip.restaurant.service.MenuService;
import creatip.restaurant.service.dto.ActionItemDTO;
import creatip.restaurant.service.dto.ActionItemOrderDTO;
import creatip.restaurant.service.dto.MenuDTO;
import creatip.restaurant.service.dto.NestedMenuItemDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final ActionRoleRepository actionRoleRepository;
    private final ActionItemRepository actionItemRepository;
    private final ActionItemOrderRepository actionItemOrderRepository;

    public MenuServiceImpl(ActionRoleRepository actionRoleRepository,
                           ActionItemRepository actionItemRepository,
                           ActionItemOrderRepository actionItemOrderRepository) {
        this.actionRoleRepository = actionRoleRepository;
        this.actionItemRepository = actionItemRepository;
        this.actionItemOrderRepository = actionItemOrderRepository;
    }

    @Override
    public MenuDTO getMenuForUser(Set<String> roleCodes) {
        if (roleCodes == null || roleCodes.isEmpty()) {
            return new MenuDTO(String.valueOf(System.currentTimeMillis()), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        }

        List<ActionRole> rolePerms = actionRoleRepository.findByRoleCodeIn(roleCodes);

        // compute effective permissions (union across roles)
        Map<Long, EffectivePerm> permMap = new HashMap<>();
        for (ActionRole ar : rolePerms) {
            if (ar.getActionItem() == null) continue;
            Long itemId = ar.getActionItem().getId();
            EffectivePerm p = permMap.computeIfAbsent(itemId, k -> new EffectivePerm());
            p.view |= ar.isViewEnable();
            p.add |= ar.isAddEnable();
            p.edit |= ar.isEditEnable();
            p.delete |= ar.isDeleteEnable();
            p.print |= ar.isPrintEnable();
        }

        Set<Long> itemIds = permMap.keySet();
        List<ActionItem> items = itemIds.isEmpty() ? Collections.emptyList() : actionItemRepository.findAllById(itemIds);

        // map ActionItem -> ActionItemDTO
        List<ActionItemDTO> itemDTOs = items.stream().map(item -> {
            EffectivePerm p = permMap.get(item.getId());
            ActionItemDTO dto = new ActionItemDTO();
            dto.setId(item.getId());
            dto.setCaption(item.getCaption());
            dto.setRouterLink(item.getRouterLink());
            if (p != null) {
                dto.setViewApplicable(p.view);
                dto.setAddApplicable(p.add);
                dto.setEditApplicable(p.edit);
                dto.setDeleteApplicable(p.delete);
                dto.setPrintApplicable(p.print);
            }
            return dto;
        }).collect(Collectors.toList());

        // load ordering info
    List<ActionItemOrder> orders = actionItemOrderRepository.findAll();
    List<ActionItemOrderDTO> orderDTOs = orders.stream().map(o -> new ActionItemOrderDTO(
        o.getId(), o.getOrderNo(), o.getItemType(), o.getPath(),
        o.getActionItem() != null ? o.getActionItem().getId() : null,
        o.getItemParent() != null ? o.getItemParent().getId() : null
    )).collect(Collectors.toList());

        // Build nested tree using orders and itemDTOs
        Map<Long, NestedMenuItemDTO> idToNode = new HashMap<>();
        for (ActionItemDTO a : itemDTOs) {
            NestedMenuItemDTO node = new NestedMenuItemDTO(a.getId(), a.getCaption(), a.getRouterLink(),
                    a.isViewApplicable(), a.isAddApplicable(), a.isEditApplicable(), a.isDeleteApplicable(), a.isPrintApplicable());
            idToNode.put(a.getId(), node);
        }

        // sort orders by orderNo to preserve ordering
        orders.sort(Comparator.comparing(ActionItemOrder::getOrderNo));

        List<NestedMenuItemDTO> roots = new ArrayList<>();
        for (ActionItemOrder o : orders) {
            Long itemId = o.getActionItem() != null ? o.getActionItem().getId() : null;
            if (!idToNode.containsKey(itemId)) continue; // skip items user has no permission for
            NestedMenuItemDTO node = idToNode.get(itemId);
            Long parentId = o.getItemParent() != null ? o.getItemParent().getId() : null;
            if (parentId == null) {
                roots.add(node);
            } else {
                NestedMenuItemDTO parent = idToNode.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(node);
                } else {
                    // parent not present in permitted items: treat as root
                    roots.add(node);
                }
            }
        }

        String version = String.valueOf(System.currentTimeMillis());
        return new MenuDTO(version, itemDTOs, orderDTOs, roots);
    }

    // simple struct to collect effective permissions
    private static class EffectivePerm {
        boolean view = false;
        boolean add = false;
        boolean edit = false;
        boolean delete = false;
        boolean print = false;
    }
}
