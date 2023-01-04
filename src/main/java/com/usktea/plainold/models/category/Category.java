package com.usktea.plainold.models.category;

import com.usktea.plainold.dtos.CategoryDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category fake() {
        return new Category(1L, "T-shirts");
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryDto toDto() {
        return new CategoryDto(id, name);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Category otherCategory = (Category) object;

        return Objects.equals(id, otherCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
