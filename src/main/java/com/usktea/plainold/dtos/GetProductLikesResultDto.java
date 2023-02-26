package com.usktea.plainold.dtos;

public class GetProductLikesResultDto {
    private Integer counts;

    public GetProductLikesResultDto() {
    }

    public GetProductLikesResultDto(Integer counts) {
        this.counts = counts;
    }

    public Integer getCounts() {
        return counts;
    }
}
