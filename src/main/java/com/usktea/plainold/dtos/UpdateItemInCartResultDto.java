package com.usktea.plainold.dtos;

import java.util.List;

public class UpdateItemInCartResultDto {
    private List<Long> updatedIds;

    public UpdateItemInCartResultDto() {
    }

    public UpdateItemInCartResultDto(List<Long> updatedIds) {
        this.updatedIds = updatedIds;
    }

    public List<Long> getUpdated() {
        return updatedIds;
    }
}
