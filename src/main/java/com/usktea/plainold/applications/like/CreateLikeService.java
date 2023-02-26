package com.usktea.plainold.applications.like;

import com.usktea.plainold.exceptions.UserAlreadyLikedProduct;
import com.usktea.plainold.models.like.Likes;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.repositories.LikeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CreateLikeService {
    private final GetUserLikesService getUserLikesService;
    private final LikeRepository likeRepository;

    public CreateLikeService(GetUserLikesService getUserLikesService,
                             LikeRepository likeRepository) {
        this.getUserLikesService = getUserLikesService;
        this.likeRepository = likeRepository;
    }

    public Long create(Username username, ProductId productId) {
        List<Likes> likes = getUserLikesService.getLikes(username, productId);

        if (!likes.isEmpty()) {
            throw new UserAlreadyLikedProduct();
        }

        Likes created = likeRepository.save(new Likes(username, productId));

        return created.id();
    }
}
