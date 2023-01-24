package com.usktea.plainold.applications;

import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.repositories.ReviewRepository;
import com.usktea.plainold.specifications.ReviewSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@SuppressWarnings("unchecked")
public class GetReviewsService {
    private final GetProductService getProductService;
    private final ReviewRepository reviewRepository;

    public GetReviewsService(GetProductService getProductService, ReviewRepository reviewRepository) {
        this.getProductService = getProductService;
        this.reviewRepository = reviewRepository;
    }

    public Page<Review> getReviews(ProductId productId, Boolean photoReviews, Integer pageNumber) {
        Product product = getProductService.find(productId);

        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);

        Specification<Review> specification = ReviewSpecification.equalProductId(product.id());

        if (photoReviews) {
            specification = specification.and(ReviewSpecification.imageUrlNotNull());
        }

        Page<Review> reviews = reviewRepository.findAll(specification, pageable);

        return reviews;
    }
}
