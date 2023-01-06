package com.usktea.plainold.models.order;

import com.usktea.plainold.dtos.OptionDto;
import com.usktea.plainold.models.option.Size;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
public class ItemOption {
    private Size size;
    private String color;

    public ItemOption() {
    }

    public ItemOption(OptionDto option) {
        setOption(option);
    }

    public ItemOption(String size, String color) {
        this.size = Size.valueOf(size);
        this.color = color;
    }

    private void setOption(OptionDto option) {
        if (option == null) {
            this.size = Size.NONE;
            this.color = "";

            return;
        }

        this.size = Size.valueOf(option.getSize());
        this.color = option.getColor();
    }

    @Enumerated(EnumType.STRING)
    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        ItemOption otherItemOption = (ItemOption) object;

        return size == otherItemOption.size
                && Objects.equals(color, otherItemOption.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, color);
    }
}
