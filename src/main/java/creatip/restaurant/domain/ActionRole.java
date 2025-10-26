package creatip.restaurant.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name="creatip_action_role")
public class ActionRole extends AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name="role_code", length = 50, nullable = false)
    private String roleCode;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private ActionItem actionItem;

    @NotNull
    @Column(name = "view_enable", nullable = false)
    private  boolean viewEnable;

    @NotNull
    @Column(name = "add_enable", nullable = false)
    private  boolean addEnable;

    @NotNull
    @Column(name = "edit_enable", nullable = false)
    private  boolean editEnable;

    @NotNull
    @Column(name = "delete_enable", nullable = false)
    private  boolean deleteEnable;

    @NotNull
    @Column(name = "print_enable", nullable = false)
    private  boolean printEnable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public ActionItem getActionItem() {
        return actionItem;
    }

    public void setActionItem(ActionItem actionItem) {
        this.actionItem = actionItem;
    }

    public boolean isViewEnable() {
        return viewEnable;
    }

    public void setViewEnable(boolean viewEnable) {
        this.viewEnable = viewEnable;
    }

    public boolean isAddEnable() {
        return addEnable;
    }

    public void setAddEnable(boolean addEnable) {
        this.addEnable = addEnable;
    }

    public boolean isEditEnable() {
        return editEnable;
    }

    public void setEditEnable(boolean editEnable) {
        this.editEnable = editEnable;
    }

    public boolean isDeleteEnable() {
        return deleteEnable;
    }

    public void setDeleteEnable(boolean deleteEnable) {
        this.deleteEnable = deleteEnable;
    }

    public boolean isPrintEnable() {
        return printEnable;
    }

    public void setPrintEnable(boolean printEnable) {
        this.printEnable = printEnable;
    }

    
}
