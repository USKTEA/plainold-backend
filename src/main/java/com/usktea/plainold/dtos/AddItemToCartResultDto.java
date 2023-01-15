package com.usktea.plainold.dtos;

public class AddItemToCartResultDto {
    private int counts;

    public AddItemToCartResultDto(int counts) {
        this.counts = counts;
    }

    public int getCounts() {
        return counts;
    }
}
