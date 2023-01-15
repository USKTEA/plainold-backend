package com.usktea.plainold.dtos;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UpdateItemInCartRequestDto {
    @NotNull
    private List<ItemDto> items;

    public UpdateItemInCartRequestDto() {
    }

    public List<ItemDto> getItems() {
        return items;
    }
}
