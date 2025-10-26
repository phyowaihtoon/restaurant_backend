package creatip.restaurant.service.dto;

import java.io.Serializable;


public class ActionItemDTO implements Serializable{
    private  Long id;

    private String caption;

    private  String routerLink;

    private  boolean viewApplicable;

    private  boolean addApplicable;

    private  boolean editApplicable;

    private  boolean deleteApplicable;

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
