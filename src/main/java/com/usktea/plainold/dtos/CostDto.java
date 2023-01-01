package com.usktea.plainold.dtos;

public class CostDto {
    private Long amount;

    public CostDto() {
    }

    public CostDto(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }
}
