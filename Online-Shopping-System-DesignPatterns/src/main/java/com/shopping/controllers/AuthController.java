package com.shopping.controllers;

import com.shopping.models.User;
import com.shopping.users.AuthManager;
import com.shopping.users.UserFactory;

public class AuthController {
    private final AuthManager auth = AuthManager.getInstance();
    private final UserFactory factory = new UserFactory();

    public User loginCustomer(String id, String name, String email) {
        User u = factory.create(UserFactory.UserType.CUSTOMER, id, name, email);
        auth.login(u); return u;
    }

    public User loginAdmin(String id, String name, String email) {
        User u = factory.create(UserFactory.UserType.ADMIN, id, name, email);
        auth.login(u); return u;
    }

    public void logout() { auth.logout(); }
    public User currentUser() { return auth.getCurrentUser(); }
}
