package com.usktea.plainold.applications;

import com.usktea.plainold.models.Product;
import com.usktea.plainold.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GetProductService {
    private final ProductRepository productRepository;

    public GetProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> list() {
        List<Product> products = productRepository.findAll();

        return products;
    }
}
