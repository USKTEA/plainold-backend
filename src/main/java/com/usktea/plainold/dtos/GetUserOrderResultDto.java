package com.usktea.plainold.dtos;

import java.util.List;

public class GetUserOrderResultDto {
    private List<OrderSummaryDto> orders;

    public GetUserOrderResultDto() {
    }

    public GetUserOrderResultDto(List<OrderSummaryDto> orders) {
        this.orders = orders;
    }

    public List<OrderSummaryDto> getOrders() {
        return orders;
    }
}
