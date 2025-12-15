package com.shopping.users;

import com.shopping.models.User;

public class UserFactory {
    public enum UserType { CUSTOMER, ADMIN }

    public User create(UserType type, String id, String name, String email) {
        return switch (type) {
            case CUSTOMER -> new Customer(id, name, email);
            case ADMIN -> new Admin(id, name, email);
        };
    }
}
