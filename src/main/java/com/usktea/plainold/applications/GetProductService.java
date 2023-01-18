package com.usktea.plainold.applications;

import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class GetProductService {
    private final ProductRepository productRepository;

    public GetProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product find(ProductId productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFound::new);

        return product;
    }
}
