package com.usktea.plainold.dtos;

import java.util.List;

public class ReviewsDto {
    private List<ReviewDto> reviews;
    private PageDto page;

    public ReviewsDto() {
    }

    public ReviewsDto(List<ReviewDto> reviews, PageDto page) {
        this.reviews = reviews;
        this.page = page;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public PageDto getPage() {
        return page;
    }
}
