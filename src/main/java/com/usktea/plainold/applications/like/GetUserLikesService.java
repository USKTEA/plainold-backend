package com.usktea.plainold.applications.like;

import com.usktea.plainold.applications.product.GetProductService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.models.like.Likes;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.LikeRepository;
import com.usktea.plainold.specifications.LikeSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class GetUserLikesService {
    private final GetUserService getUserService;
    private final GetProductService getProductService;
    private final LikeRepository likeRepository;

    public GetUserLikesService(GetUserService getUserService,
                               GetProductService getProductService,
                               LikeRepository likeRepository) {
        this.getUserService = getUserService;
        this.getProductService = getProductService;
        this.likeRepository = likeRepository;
    }

    public List<Likes> getLikes(Username username, ProductId productId) {
        Users users = getUserService.find(username);
        Product product = getProductService.find(productId);

        Specification<Likes> specification = Specification.where(
                LikeSpecification.withUsername(users.username())
                        .and(LikeSpecification.withProductId(product.id())));

        List<Likes> likes = likeRepository.findAll(specification);

        return likes;
    }

    public List<Likes> getLikes(Username username) {
        Users users = getUserService.find(username);

        Specification<Likes> specification = LikeSpecification.withUsername(users.username());

        List<Likes> likes = likeRepository.findAll(specification);

        return likes;
    }
}
