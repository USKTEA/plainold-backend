package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.GetProductService;
import com.usktea.plainold.dtos.PageDto;
import com.usktea.plainold.dtos.ProductDetailDto;
import com.usktea.plainold.dtos.ProductsDto;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.CategoryId;
import com.usktea.plainold.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
public class ProductController {
    private final GetProductService getProductService;

    public ProductController(GetProductService getProductService) {
        this.getProductService = getProductService;
    }

    @GetMapping
    public ProductsDto list(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        Page<Product> found = getProductService.list(new CategoryId(categoryId), page);

        PageDto pageDto = new PageDto(page, found.getTotalPages());

        ProductsDto products = new ProductsDto(
                found.stream()
                        .map(Product::toDto)
                        .collect(Collectors.toList())
        );

        products.setPage(pageDto);

        return products;
    }

    @GetMapping("{id}")
    public ProductDetailDto product(
            @PathVariable Long id
    ) {
        Product product = getProductService.product(id);

        return product.toDetailDto();
    }

    @ExceptionHandler(ProductNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String productNotFound(Exception exception) {
        return exception.getMessage();
    }
}
