package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.GetProductService;
import com.usktea.plainold.dtos.ProductsDto;
import com.usktea.plainold.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
public class ProductController {
    private final GetProductService getProductService;

    public ProductController(GetProductService getProductService) {
        this.getProductService = getProductService;
    }

    @GetMapping
    public ProductsDto list() {
        List<Product> products = getProductService.list();

        return new ProductsDto(
                products.stream()
                        .map(Product::toDto)
                        .collect(Collectors.toList())
        );
    }
}
