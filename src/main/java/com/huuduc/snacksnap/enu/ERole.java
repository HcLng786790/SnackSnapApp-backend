package com.huuduc.snacksnap.enu;

public enum ERole {

    Admin("ADMIN"),
    User("USER");

    private final String NAME;

    ERole(String NAME) {
        this.NAME = NAME;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
