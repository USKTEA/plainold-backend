package com.usktea.plainold.dtos;

public class ColorDto {
    private String name;
    private Integer red;
    private Integer green;
    private Integer blue;

    public ColorDto() {
    }

    public ColorDto(String name,
                    RgbDto red,
                    RgbDto green,
                    RgbDto blue) {
        this.name = name;
        this.red = red.value();
        this.green = green.value();
        this.blue = blue.value();
    }

    public String getName() {
        return name;
    }

    public Integer getRed() {
        return red;
    }

    public Integer getGreen() {
        return green;
    }

    public Integer getBlue() {
        return blue;
    }
}
