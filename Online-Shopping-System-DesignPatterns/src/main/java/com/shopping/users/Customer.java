package com.shopping.users;

import com.shopping.models.User;

public class Customer extends User {
    public Customer(String id, String name, String email) { super(id, name, email); }
    @Override public String getRole() { return "Customer"; }
}
