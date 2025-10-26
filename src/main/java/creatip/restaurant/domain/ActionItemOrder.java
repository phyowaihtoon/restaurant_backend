package creatip.restaurant.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name="creatip_action_item_order")
public class ActionItemOrder extends AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull
    @Column(name="order_no", nullable = false)
    private Long orderNo;

    @NotNull
    @Size(min = 1, max = 5)
    @Column(name="item_type", length = 5, nullable = false)
    private  String itemType; //ITEM OR GROUP

    @NotNull
    @Size(min = 1, max = 255)
    @Column(length = 255, nullable = false)
    private  String path;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private ActionItem actionItem;

    @ManyToOne
    @JoinColumn(name="item_parent_id", referencedColumnName = "id")
    private ActionItem itemParent;

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

    public ActionItem getActionItem() {
        return actionItem;
    }

    public void setActionItem(ActionItem actionItem) {
        this.actionItem = actionItem;
    }

    public ActionItem getItemParent() {
        return itemParent;
    }

    public void setItemParent(ActionItem itemParent) {
        this.itemParent = itemParent;
    }

    

}
