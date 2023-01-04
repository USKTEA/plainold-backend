package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.GetProductDetailService;
import com.usktea.plainold.applications.GetProductsService;
import com.usktea.plainold.dtos.PageDto;
import com.usktea.plainold.dtos.ProductDetail;
import com.usktea.plainold.dtos.ProductDetailDto;
import com.usktea.plainold.dtos.ProductsDto;
import com.usktea.plainold.exceptions.CategoryNotFound;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.category.CategoryId;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
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
    private final GetProductsService getProductsService;
    private GetProductDetailService getProductDetailService;

    public ProductController(GetProductsService getProductsService, GetProductDetailService getProductDetailService) {
        this.getProductsService = getProductsService;
        this.getProductDetailService = getProductDetailService;
    }

    @GetMapping
    public ProductsDto list(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        Page<Product> found = getProductsService.list(new CategoryId(categoryId), page);

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
        ProductDetail productDetail = getProductDetailService.detail(new ProductId(id));

        return productDetail.toDto();
    }

    @ExceptionHandler(ProductNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String productNotFound(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CategoryNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String categoryNotFound(Exception exception) {
        return exception.getMessage();
    }
}
