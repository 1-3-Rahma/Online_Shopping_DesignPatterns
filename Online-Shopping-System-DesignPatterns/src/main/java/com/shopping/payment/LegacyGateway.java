package com.shopping.payment;

import java.util.UUID;

public class LegacyGateway {
    private final String token;

    public LegacyGateway(String token) {
        this.token = token;
    }

    public String makePayment(double amount) {
        if (amount <= 0) return null;
        return "LEGACY-" + UUID.randomUUID();
    }

    public String getToken() { return token; }
}
