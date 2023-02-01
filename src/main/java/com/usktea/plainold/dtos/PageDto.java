package com.usktea.plainold.dtos;

public class PageDto {
    private Integer current;
    private Integer total;
    private Long counts;

    public PageDto() {
    }

    public PageDto(Integer current, Integer total, Long counts) {
        this.current = current;
        this.total = total;
        this.counts = counts;
    }

    public Long getCounts() {
        return counts;
    }

    public Integer getCurrent() {
        return current;
    }

    public Integer getTotal() {
        return total;
    }
}
