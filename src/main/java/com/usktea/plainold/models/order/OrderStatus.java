package com.usktea.plainold.models.order;

public enum OrderStatus {
    PAYMENT_WAITING("입금대기"),
    PREPARING("배송준비중"),
    SHIPPED("배송처리"),
    DELIVERING("배송중"),
    DELIVERY_COMPLETED("배송완료");

    private String status;

    OrderStatus() {
    }

    OrderStatus(String status) {
        this.status = status;
    }

    public String value() {
        return status;
    }
}
