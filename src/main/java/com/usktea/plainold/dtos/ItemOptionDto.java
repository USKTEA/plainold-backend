package com.usktea.plainold.dtos;

public class ItemOptionDto {
    private String size;
    private String color;

    public ItemOptionDto() {
    }

    public ItemOptionDto(String size, String color) {
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }
}
