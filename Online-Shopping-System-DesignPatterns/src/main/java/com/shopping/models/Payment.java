package com.shopping.models;

public class Payment {
    private final String methodName;
    private final double amount;
    private final boolean success;
    private final String transactionId;

    public Payment(String methodName, double amount, boolean success, String transactionId) {
        this.methodName = methodName;
        this.amount = amount;
        this.success = success;
        this.transactionId = transactionId;
    }

    public String getMethodName() { return methodName; }
    public double getAmount() { return amount; }
    public boolean isSuccess() { return success; }
    public String getTransactionId() { return transactionId; }
}
