package creatip.restaurant.service.dto;

import java.io.Serializable;
import java.util.List;

public class AccountDTO implements Serializable{

    private AdminUserDTO account;

    private List<NestedMenuItemDTO> menuTree;

    public AdminUserDTO getAccount() {
        return account;
    }

    public void setAccount(AdminUserDTO account) {
        this.account = account;
    }

    public List<NestedMenuItemDTO> getMenuTree() {
        return menuTree;
    }

    public void setMenuTree(List<NestedMenuItemDTO> menuTree) {
        this.menuTree = menuTree;
    }


    
}
