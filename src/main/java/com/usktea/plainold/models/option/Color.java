package com.usktea.plainold.models.option;

import com.usktea.plainold.dtos.ColorDto;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Color {
    private String name;

    @AttributeOverride(name = "value", column = @Column(name = "red"))
    private Rgb red;

    @AttributeOverride(name = "value", column = @Column(name = "green"))
    private Rgb green;

    @AttributeOverride(name = "value", column = @Column(name = "blue"))
    private Rgb blue;

    public Color() {
    }

    public Color(String name, Rgb red, Rgb green, Rgb blue) {
        this.name = name;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static Color of(ColorDto color) {
        return new Color(
                color.getName(),
                new Rgb(color.getRed()),
                new Rgb(color.getGreen()),
                new Rgb(color.getBlue())
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rgb getRed() {
        return red;
    }

    public void setRed(Rgb red) {
        this.red = red;
    }

    public Rgb getGreen() {
        return green;
    }

    public void setGreen(Rgb green) {
        this.green = green;
    }

    public Rgb getBlue() {
        return blue;
    }

    public void setBlue(Rgb blue) {
        this.blue = blue;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Color otherColor = (Color) object;

        return Objects.equals(name, otherColor.name)
                && Objects.equals(red, otherColor.red)
                && Objects.equals(green, otherColor.green)
                && Objects.equals(blue, otherColor.blue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, red, green, blue);
    }

    public ColorDto toDto() {
        return new ColorDto(name,
                red.toDto(),
                green.toDto(),
                blue.toDto()
        );
    }
}
