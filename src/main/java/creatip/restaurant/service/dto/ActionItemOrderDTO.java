package creatip.restaurant.service.dto;

import java.io.Serializable;

/**
 * DTO for action item ordering / grouping information.
 */
public class ActionItemOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long orderNo;

    private String itemType;

    private String path;

    private Long itemId;

    private Long itemParentId;

    public ActionItemOrderDTO() {}

    public ActionItemOrderDTO(Long id, Long orderNo, String itemType, String path, Long itemId, Long itemParentId) {
        this.id = id;
        this.orderNo = orderNo;
        this.itemType = itemType;
        this.path = path;
        this.itemId = itemId;
        this.itemParentId = itemParentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemParentId() {
        return itemParentId;
    }

    public void setItemParentId(Long itemParentId) {
        this.itemParentId = itemParentId;
    }
}
