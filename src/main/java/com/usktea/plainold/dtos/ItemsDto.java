package com.usktea.plainold.dtos;

import java.util.ArrayList;
import java.util.List;

public class ItemsDto {
    private List<ItemDto> items = new ArrayList<>();

    public ItemsDto() {
    }

    public ItemsDto(List<ItemDto> items) {
        this.items = items;
    }

    public List<ItemDto> getItems() {
        return items;
    }
}
