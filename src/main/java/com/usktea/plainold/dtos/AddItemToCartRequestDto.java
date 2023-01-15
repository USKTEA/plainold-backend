package com.usktea.plainold.dtos;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AddItemToCartRequestDto {
    @NotNull
    private List<ItemDto> items;

    public AddItemToCartRequestDto() {
    }

    public List<ItemDto> getItems() {
        return items;
    }
}
