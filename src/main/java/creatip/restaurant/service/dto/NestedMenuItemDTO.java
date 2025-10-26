package creatip.restaurant.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Nested menu item used by the server to return a hierarchical menu structure.
 */
public class NestedMenuItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String caption;
    private String routerLink;
    private boolean viewApplicable;
    private boolean addApplicable;
    private boolean editApplicable;
    private boolean deleteApplicable;
    private boolean printApplicable;

    private List<NestedMenuItemDTO> children = new ArrayList<>();

    public NestedMenuItemDTO() {}

    public NestedMenuItemDTO(Long id, String caption, String routerLink, boolean viewApplicable, boolean addApplicable,
                             boolean editApplicable, boolean deleteApplicable, boolean printApplicable) {
        this.id = id;
        this.caption = caption;
        this.routerLink = routerLink;
        this.viewApplicable = viewApplicable;
        this.addApplicable = addApplicable;
        this.editApplicable = editApplicable;
        this.deleteApplicable = deleteApplicable;
        this.printApplicable = printApplicable;
    }

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

    public List<NestedMenuItemDTO> getChildren() {
        return children;
    }

    public void setChildren(List<NestedMenuItemDTO> children) {
        this.children = children;
    }
}
