package com.usktea.plainold.dtos;

import java.util.List;
import java.util.stream.Stream;

public class OrderSummaryDto {
    private String orderNumber;
    private List<OrderLineDto> orderLines;
    private String status;
    private String createdAt;

    public OrderSummaryDto() {
    }

    public OrderSummaryDto(String orderNumber,
                           List<OrderLineDto> orderLines,
                           String status,
                           String createdAt) {
        this.orderNumber = orderNumber;
        this.orderLines = orderLines;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public List<OrderLineDto> getOrderLines() {
        return orderLines;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
