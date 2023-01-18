package com.usktea.plainold.applications;

import com.usktea.plainold.builders.ProductDetailBuilder;
import com.usktea.plainold.builders.ProductDetailDirector;
import com.usktea.plainold.dtos.ProductDetail;
import com.usktea.plainold.models.option.Option;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.repositories.OptionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class GetProductDetailService {
    private final GetProductService getProductService;
    private final OptionRepository optionRepository;

    public GetProductDetailService(GetProductService getProductService, OptionRepository optionRepository) {
        this.getProductService = getProductService;
        this.optionRepository = optionRepository;
    }

    public ProductDetail detail(ProductId id) {
//        Product fake = Product.fake(id);
//
//        productRepository.save(fake);

        Product product = getProductService.find(id);

        Option option = optionRepository.findByProductId(id)
                .orElse(null);

        ProductDetail productDetail = getDetail(product, option);

        return productDetail;
    }

    private ProductDetail getDetail(Product product, Option option) {
        ProductDetailDirector director = new ProductDetailDirector();

        ProductDetailBuilder builder = new ProductDetailBuilder();

        if (option == null) {
            director.productDetailWithoutOption(builder, product);
        }

        if (option != null) {
            director.productDetailWithOption(builder, product, option);
        }

        ProductDetail productDetail = builder.getResult();

        return productDetail;
    }
}
