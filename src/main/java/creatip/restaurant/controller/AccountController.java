package creatip.restaurant.controller;

import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import creatip.restaurant.security.SecurityUtils;
import creatip.restaurant.service.MenuService;
import creatip.restaurant.service.UserService;
import creatip.restaurant.service.dto.AccountDTO;
import creatip.restaurant.service.dto.AdminUserDTO;
import creatip.restaurant.service.dto.MenuDTO;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final MenuService menuService;
    private final UserService userService;

    public AccountController(MenuService menuService, UserService userService) {
        this.menuService = menuService;
        this.userService = userService;
    }

        /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public ResponseEntity<AccountDTO> getAccount() {
        AdminUserDTO adminUserDTO = userService
            .getUserWithRoles()
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));

        Set<String> roleCodes = SecurityUtils.getCurrentUserAuthorities();

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount(adminUserDTO);

        MenuDTO menuDTO = menuService.getMenuForUser(roleCodes);
        if(menuDTO != null) {
           accountDTO.setMenuTree(menuDTO.getTree());
        }
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/account/menu")
    public ResponseEntity<MenuDTO> getMenu( ) {
        Set<String> roleCodes = SecurityUtils.getCurrentUserAuthorities();

        MenuDTO menu = menuService.getMenuForUser(roleCodes);
        
        return ResponseEntity.ok(menu);
    }

    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }
}
