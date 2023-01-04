package com.usktea.plainold.dtos;

import com.usktea.plainold.models.option.Color;

import java.util.Set;
import java.util.stream.Collectors;

public class OptionDataDto {
    private Set<String> sizes;
    private Set<ColorDto> colors;

    public OptionDataDto() {
    }

    public OptionDataDto(Set<String> sizes, Set<Color> colors) {
        this.sizes = sizes;
        setColors(colors);
    }

    public Set<String> getSizes() {
        return sizes;
    }

    public Set<ColorDto> getColors() {
        return colors;
    }

    private void setColors(Set<Color> colors) {
        this.colors = colors.stream()
                .map(Color::toDto)
                .collect(Collectors.toSet());
    }
}
