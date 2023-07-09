package com.tainika.qlnt.qlnt.constants;

import java.util.Set;
import com.google.common.collect.Sets;

import static com.tainika.qlnt.qlnt.constants.AppUserPermission.*;

public enum AppUserRole {
    ADMIN("ADMIN", Sets.newHashSet(AM01)),
    MANAGER("MANAGER", Sets.newHashSet(MR01)),
    USER("USER", Sets.newHashSet(UR01, UR02)),
    GUEST("GUEST", Sets.newHashSet(GR01));

    private String code;
    private final Set<AppUserPermission> permissions;

    AppUserRole(String code, Set<AppUserPermission> permissions) {
        this.code = code;
        this.permissions = permissions;
    }

    public String getCode() {
        return code;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }
}
