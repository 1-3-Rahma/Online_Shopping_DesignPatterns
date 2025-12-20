package com.shopping.models;

public abstract class User {
    private final String id;
    private final String name;
    private final String email;

    protected User(String id, String name, String email) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("User id required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("User name required");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email required");
        this.id = id.trim();
        this.name = name.trim();
        this.email = email.trim();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public abstract String getRole();

    @Override
    public String toString() {
        return "[" + getRole() + "] " + name + " <" + email + "> (id=" + id + ")";
    }
}
