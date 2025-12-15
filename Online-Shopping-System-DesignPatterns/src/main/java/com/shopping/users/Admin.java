package com.shopping.users;

import com.shopping.models.User;

public class Admin extends User {
    public Admin(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public String getRole() { return "Admin"; }
}
