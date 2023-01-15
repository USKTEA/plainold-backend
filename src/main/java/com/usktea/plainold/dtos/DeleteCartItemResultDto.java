package com.usktea.plainold.dtos;

import java.util.List;

public class DeleteCartItemResultDto {
    private List<Long> deleted;

    public DeleteCartItemResultDto() {
    }

    public DeleteCartItemResultDto(List<Long> deleted) {
        this.deleted = deleted;
    }

    public List<Long> getDeleted() {
        return deleted;
    }
}
