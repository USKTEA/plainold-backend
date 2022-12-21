package com.usktea.plainold.dtos;

import java.util.ArrayList;
import java.util.List;

public class CategoriesDto {
    private List<CategoryDto> categories = new ArrayList<>();

    public CategoriesDto() {
    }

    public CategoriesDto(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }
}
