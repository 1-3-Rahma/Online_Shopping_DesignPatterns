package com.shopping.util;

import java.util.UUID;
// IDs
public final class Ids {
    private Ids() {}
    public static String shortId(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
