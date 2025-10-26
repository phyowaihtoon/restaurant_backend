package creatip.restaurant.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name ="creatip_action_item")
public class ActionItem extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(length = 255, nullable = false)
    private String caption;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name="router_link", length = 255, nullable = false)
    private  String routerLink;

    @NotNull
    @Column(name = "view_applicable", nullable = false)
    private  boolean viewApplicable;

    @NotNull
    @Column(name = "add_applicable", nullable = false)
    private  boolean addApplicable;

    @NotNull
    @Column(name = "edit_applicable", nullable = false)
    private  boolean editApplicable;

    @NotNull
    @Column(name = "delete_applicable", nullable = false)
    private  boolean deleteApplicable;

    @NotNull
    @Column(name = "print_applicable", nullable = false)
    private  boolean printApplicable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getRouterLink() {
        return routerLink;
    }

    public void setRouterLink(String routerLink) {
        this.routerLink = routerLink;
    }

    public boolean isViewApplicable() {
        return viewApplicable;
    }

    public void setViewApplicable(boolean viewApplicable) {
        this.viewApplicable = viewApplicable;
    }

    public boolean isAddApplicable() {
        return addApplicable;
    }

    public void setAddApplicable(boolean addApplicable) {
        this.addApplicable = addApplicable;
    }

    public boolean isEditApplicable() {
        return editApplicable;
    }

    public void setEditApplicable(boolean editApplicable) {
        this.editApplicable = editApplicable;
    }

    public boolean isDeleteApplicable() {
        return deleteApplicable;
    }

    public void setDeleteApplicable(boolean deleteApplicable) {
        this.deleteApplicable = deleteApplicable;
    }

    public boolean isPrintApplicable() {
        return printApplicable;
    }

    public void setPrintApplicable(boolean printApplicable) {
        this.printApplicable = printApplicable;
    }
}
