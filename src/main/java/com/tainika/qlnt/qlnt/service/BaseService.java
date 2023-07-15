package com.tainika.qlnt.qlnt.service;

import com.tainika.qlnt.qlnt.constants.AppUserRole;
import com.tainika.qlnt.qlnt.constants.Status;
import com.tainika.qlnt.qlnt.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.tainika.qlnt.qlnt.constants.AppUserPermission.AM01;
import static com.tainika.qlnt.qlnt.constants.AppUserPermission.UR01;

@Service
public class BaseService {
    private final RoleService roleService;

    @Autowired
    public BaseService(RoleService roleService) {
        this.roleService = roleService;
    }

    public static boolean hasAdminPermission(List<String> authorities) {
        return authorities.contains(AM01.getPermission());
    }

    public static boolean hasUserPermission(List<String> authorities) {
        return authorities.contains(UR01.getPermission());
    }

    public static void checkAdminPermissionWithAction(String action) throws IllegalAccessException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        boolean isAdmin = BaseService.hasAdminPermission(authorities);
        if (!isAdmin) {
             throw MessageResultService.builder()
                .content("Permission deny !!")
                .action(String.valueOf(action))
                .build().withFailureResponse()
                .throwIllegalAccessException();
        }
    }

    public static boolean checkRoleCodeEnum(String roleCode) {
        return Arrays.stream(AppUserRole.values()).anyMatch(r -> r.getCode().equals(roleCode));
    }

    public Role getOrCreateRole(AppUserRole roleEnum) {
        Role role = roleService.findByRoleCodeFromDB(roleEnum);
        if (role == null) {
            MessageResultService<?> newRole = roleService.create(roleEnum);
            if (newRole.getStatus().equals(Status.COMMON.ERROR)) return null;

            role = (Role) newRole.getItem();
        }

        return role;
    }
}
