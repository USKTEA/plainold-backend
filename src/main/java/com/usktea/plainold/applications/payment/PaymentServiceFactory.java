package com.usktea.plainold.applications.payment;

import java.util.Map;

public class PaymentServiceFactory {
    private Map<String, PaymentService> services;

    public PaymentServiceFactory(Map<String, PaymentService> services) {
        this.services = services;
    }

    public PaymentService getmentPayService(String provider) {
        return services.get(provider);
    }
}
