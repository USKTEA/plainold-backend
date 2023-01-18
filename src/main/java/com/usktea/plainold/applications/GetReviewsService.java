package com.usktea.plainold.applications;

import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.repositories.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class GetReviewsService {
    private final GetProductService getProductService;
    private final ReviewRepository reviewRepository;

    public GetReviewsService(GetProductService getProductService, ReviewRepository reviewRepository) {
        this.getProductService = getProductService;
        this.reviewRepository = reviewRepository;
    }

    public Page<Review> getReviews(ProductId productId, Integer pageNumber) {
        Product product = getProductService.find(productId);

        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);

        Page<Review> reviews = reviewRepository.findAllByProductId(product.id(), pageable);

        return reviews;
    }
}
