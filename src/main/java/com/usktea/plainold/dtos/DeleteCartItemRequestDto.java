package com.usktea.plainold.dtos;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DeleteCartItemRequestDto {
    @NotNull
    private List<ItemDto> items;

    public DeleteCartItemRequestDto() {
    }

    public List<ItemDto> getItems() {
        return items;
    }
}
