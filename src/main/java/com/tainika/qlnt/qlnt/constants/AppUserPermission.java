package com.tainika.qlnt.qlnt.constants;

public enum AppUserPermission {
    AM01("AM01"),

    MR01("MR01"),

    UR01("UR01"),
    UR02("UR02"),

    GR01("GR01");

    AppUserPermission(String permission) {
        this.permission = permission;
    }
    private String permission;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
