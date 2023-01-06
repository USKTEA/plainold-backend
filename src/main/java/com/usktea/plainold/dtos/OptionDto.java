package com.usktea.plainold.dtos;

public class OptionDto {
    private String size;
    private String color;

    public OptionDto() {
    }

    public OptionDto(String size, String color) {
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
