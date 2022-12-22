package com.usktea.plainold.dtos;

public class PageDto {
    private Integer page;
    private Integer total;

    public PageDto() {
    }

    public PageDto(Integer page, Integer total) {
        this.page = page;
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getTotal() {
        return total;
    }
}
