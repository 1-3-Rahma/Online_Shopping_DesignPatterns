package com.shopping.users;

import com.shopping.models.User;

public class AuthManager {
    private static final AuthManager INSTANCE = new AuthManager();
    private User currentUser;

    private AuthManager() {}

    public static AuthManager getInstance() { return INSTANCE; }
    public void login(User user) { this.currentUser = user; }
    public void logout() { this.currentUser = null; }
    public User getCurrentUser() { return currentUser; }
    public boolean isLoggedIn() { return currentUser != null; }
}
