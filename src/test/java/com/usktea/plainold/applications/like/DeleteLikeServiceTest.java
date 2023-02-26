package com.usktea.plainold.applications.like;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.LikeNotExists;
import com.usktea.plainold.exceptions.NotHaveDeleteLikeAuthority;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.like.Likes;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class DeleteLikeServiceTest {
    private GetUserService getUserService;
    private LikeRepository likeRepository;
    private DeleteLikeService deleteLikeService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        likeRepository = mock(LikeRepository.class);
        deleteLikeService = new DeleteLikeService(getUserService, likeRepository);
    }

    @Test
    void whenUserNotExist() {
        Username username = new Username("notExists@gmail.com");
        Long likeId = 1L;

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class, () -> deleteLikeService.delete(username, likeId));
    }

    @Test
    void whenLikeNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long likeId = 9_999_999L;

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(likeRepository.findById(likeId)).willReturn(Optional.empty());

        assertThrows(LikeNotExists.class, () -> deleteLikeService.delete(username, likeId));
    }

    @Test
    void whenTryToDeleteNotHisOwnLike() {
        Username username = new Username("tjrxo1234@gmail.com");
        Username otherUser = new Username("notTjrxo1234@gmail.com");
        Long likeId = 1L;

        given(getUserService.find(otherUser)).willReturn(Users.fake(otherUser));
        given(likeRepository.findById(likeId)).willReturn(Optional.of(Likes.fake(username)));

        assertThrows(NotHaveDeleteLikeAuthority.class, () -> deleteLikeService.delete(otherUser, likeId));
    }

    @Test
    void whenDeleteLikeSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long likeId = 1L;

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(likeRepository.findById(likeId)).willReturn(Optional.of(Likes.fake(username)));

        Long deletedId = deleteLikeService.delete(username, likeId);

        assertThat(deletedId).isEqualTo(likeId);

        verify(likeRepository).delete(any(Likes.class));
    }
}
