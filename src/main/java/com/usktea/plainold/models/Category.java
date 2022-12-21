package com.usktea.plainold.models;

import com.usktea.plainold.dtos.CategoryDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Category fake() {
        return new Category(1L, "T-shirts");
    }

    public CategoryDto toDto() {
        return new CategoryDto(id, name);
    }
}
