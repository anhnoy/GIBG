package com.Auton.gibg.role;

public enum Role {
    ADMIN(1),
    CUSTOMER(3),
    EMPLOYEE(2);

    private final int roleId;

    Role(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
