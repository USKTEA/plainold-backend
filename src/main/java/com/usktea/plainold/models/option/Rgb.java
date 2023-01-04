package com.usktea.plainold.models.option;

import com.usktea.plainold.dtos.RgbDto;
import com.usktea.plainold.exceptions.InvalidRgbValue;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Rgb {
    private Integer value;

    public Rgb() {
    }

    public Rgb(Integer value) {
        setValue(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(Integer value) {
        if (value > 255 || value < 0) {
            throw new InvalidRgbValue();
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Rgb otherRgb = (Rgb) object;

        return Objects.equals(value, otherRgb.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public RgbDto toDto() {
        return new RgbDto(value);
    }
}
