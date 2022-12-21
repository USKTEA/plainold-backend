package com.usktea.plainold.controllers;

import com.usktea.plainold.dtos.CategoriesDto;
import com.usktea.plainold.models.Category;
import com.usktea.plainold.applications.GetCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("categories")
public class CategoryController {
    private GetCategoryService getCategoryService;

    public CategoryController(GetCategoryService getCategoryService) {
        this.getCategoryService = getCategoryService;
    }

    @GetMapping
    public CategoriesDto list() {
        List<Category> categories = getCategoryService.list();

        return new CategoriesDto(categories.stream()
                .map(Category::toDto)
                .collect(Collectors.toList()));
    }
}
