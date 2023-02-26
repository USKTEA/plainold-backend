package com.usktea.plainold.applications.like;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.LikeNotExists;
import com.usktea.plainold.exceptions.NotHaveDeleteLikeAuthority;
import com.usktea.plainold.exceptions.UsernameNotMatch;
import com.usktea.plainold.models.like.Likes;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.LikeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DeleteLikeService {
    private final GetUserService getUserService;
    private final LikeRepository likeRepository;

    public DeleteLikeService(GetUserService getUserService, LikeRepository likeRepository) {
        this.getUserService = getUserService;
        this.likeRepository = likeRepository;
    }

    public Long delete(Username username, Long id) {
        Users user = getUserService.find(username);

        Likes like = likeRepository.findById(id).orElseThrow(LikeNotExists::new);

        try {
            like.authenticate(user.username());
        } catch (UsernameNotMatch usernameNotMatch) {
            throw new NotHaveDeleteLikeAuthority(usernameNotMatch.getMessage());
        }

        likeRepository.delete(like);

        return like.id();
    }
}
