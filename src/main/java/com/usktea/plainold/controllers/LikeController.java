package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.like.CountProductLikesService;
import com.usktea.plainold.applications.like.CreateLikeService;
import com.usktea.plainold.applications.like.DeleteLikeService;
import com.usktea.plainold.applications.like.GetUserLikesService;
import com.usktea.plainold.dtos.CreateLikeRequestDto;
import com.usktea.plainold.dtos.CreateLikeResultDto;
import com.usktea.plainold.dtos.DeleteLikeResultDto;
import com.usktea.plainold.dtos.GetProductLikesResultDto;
import com.usktea.plainold.dtos.LikesDtos;
import com.usktea.plainold.exceptions.CountProductLikesFailed;
import com.usktea.plainold.exceptions.CreateLikeFailed;
import com.usktea.plainold.exceptions.DeleteLikeFailed;
import com.usktea.plainold.exceptions.GetUserLikesFailed;
import com.usktea.plainold.exceptions.NotHaveDeleteLikeAuthority;
import com.usktea.plainold.models.like.Likes;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("likes")
public class LikeController {
    private final CountProductLikesService countProductLikesService;
    private final GetUserLikesService getUserLikesService;
    private final CreateLikeService createLikeService;
    private final DeleteLikeService deleteLikeService;

    public LikeController(CountProductLikesService countProductLikesService,
                          GetUserLikesService getUserLikesService,
                          CreateLikeService createLikeService,
                          DeleteLikeService deleteLikeService) {
        this.countProductLikesService = countProductLikesService;
        this.getUserLikesService = getUserLikesService;
        this.createLikeService = createLikeService;
        this.deleteLikeService = deleteLikeService;
    }

    @GetMapping
    public GetProductLikesResultDto productLikes(
            @RequestParam Long productId
    ) {
        try {
            Integer counts = countProductLikesService.counts(new ProductId(productId));

            return new GetProductLikesResultDto(counts);
        } catch (Exception exception) {
            throw new CountProductLikesFailed(exception.getMessage());
        }
    }

    @GetMapping("me")
    public LikesDtos getLikeByUserWithProduct(
            @RequestAttribute Username username,
            @RequestParam(required = false) Long productId,
            HttpServletResponse response
    ) {
        List<Likes> likes = null;

        try {
            if (Objects.nonNull(productId)) {
                likes = getUserLikesService.getLikes(username, new ProductId(productId));
            }

            if (Objects.isNull(productId)) {
                likes = getUserLikesService.getLikes(username);
            }

            if (likes.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }

            return new LikesDtos(likes.stream()
                    .map((like) -> like.toDto())
                    .collect(Collectors.toList()));
        } catch (Exception exception) {
            throw new GetUserLikesFailed(exception.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateLikeResultDto create(
            @RequestAttribute Username username,
            @Valid @RequestBody CreateLikeRequestDto createLikeRequestDto
    ) {
        try {
            ProductId productId = new ProductId(createLikeRequestDto.getProductId());

            Long created = createLikeService.create(username, productId);

            return new CreateLikeResultDto(created);
        } catch (Exception exception) {
            throw new CreateLikeFailed(exception.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public DeleteLikeResultDto delete(
            @RequestAttribute Username username,
            @PathVariable Long id
    ) {
        try {
            Long deleted = deleteLikeService.delete(username, id);

            return new DeleteLikeResultDto(deleted);
        } catch (NotHaveDeleteLikeAuthority notHaveDeleteLikeAuthority) {
            throw notHaveDeleteLikeAuthority;
        } catch (Exception exception) {
            throw new DeleteLikeFailed(exception.getMessage());
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String requestInformationNotComplete(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(NotHaveDeleteLikeAuthority.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String notHaveDeleteAuthority(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CountProductLikesFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String countProductLikesFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(GetUserLikesFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String getUserLikesFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CreateLikeFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String createLikeFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(DeleteLikeFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String deleteLikeFail(Exception exception) {
        return exception.getMessage();
    }
}
