package com.usktea.plainold.models.option;

import com.usktea.plainold.dtos.OptionDataDto;

import java.util.Set;

public class OptionData {
    private Set<String> sizes;
    private Set<Color> colors;

    public OptionData() {
    }

    public OptionData(Set<String> sizes, Set<Color> colors) {
        this.sizes = sizes;
        this.colors = colors;
    }

    public static OptionData fake() {
        return new OptionData(
                Set.of("M", "L", "Xl"),
                Set.of(new Color("Red", new Rgb(255), new Rgb(0), new Rgb(0)))
        );
    }

    public Set<String> getSizes() {
        return sizes;
    }

    public Set<Color> getColors() {
        return colors;
    }

    public OptionDataDto toDto() {
        return new OptionDataDto(sizes, colors);
    }
}
