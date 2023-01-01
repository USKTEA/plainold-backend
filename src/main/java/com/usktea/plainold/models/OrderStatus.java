package com.usktea.plainold.models;

import javax.persistence.Column;

public enum OrderStatus {
    PAYMENT_WAITING("입금대기"),
    PREPARING("배송준비중"),
    SHIPPED("배송처리"),
    DELIVERING("배송중"),
    DELIVERY_COMPLETED("배송완료");

    @Column(name = "orderStatus")
    private String status;

    OrderStatus() {
    }

    OrderStatus(String status) {
        this.status = status;
    }
}
