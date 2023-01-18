package com.usktea.plainold.dtos;

public class PageDto {
    private Integer current;
    private Integer total;

    public PageDto() {
    }

    public PageDto(Integer current, Integer total) {
        this.current = current;
        this.total = total;
    }

    public Integer getCurrent() {
        return current;
    }

    public Integer getTotal() {
        return total;
    }
}
