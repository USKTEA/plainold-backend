package com.usktea.plainold.applications.like;

import com.usktea.plainold.applications.product.GetProductService;
import com.usktea.plainold.models.like.Likes;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.repositories.LikeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CountProductLikesService {
    private final GetProductService getProductService;
    private final LikeRepository likeRepository;

    public CountProductLikesService(GetProductService getProductService, LikeRepository likeRepository) {
        this.getProductService = getProductService;
        this.likeRepository = likeRepository;
    }

    public Integer counts(ProductId productId) {
        Product product = getProductService.find(productId);
        List<Likes> likes = likeRepository.findAllByProductId(product.id());

        return likes.size();
    }
}
