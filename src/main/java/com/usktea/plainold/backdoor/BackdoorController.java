package com.usktea.plainold.backdoor;

import com.usktea.plainold.models.option.Option;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Password;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.OptionRepository;
import com.usktea.plainold.repositories.ReviewRepository;
import com.usktea.plainold.repositories.UserRepository;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("backdoor")
public class BackdoorController {
    private OptionRepository optionRepository;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;

    public BackdoorController(OptionRepository optionRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.optionRepository = optionRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/option")
    public String option() {
        ProductId productId = new ProductId(1L);

        Option option = Option.fake(productId);

        option.addOptions();

        optionRepository.save(option);

        return "ok";
    }

    @GetMapping("/users")
    public String user() {
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        Users user1 = Users.fake(new Username("tjrxo1234@gmail.com"));
        Users user2 = Users.fake(new Username("rlatjrxo1234@gmail.com"));
        Users admin = Users.fake(Role.ADMIN);

        user1.changePassword(new Password("Password1234!"), passwordEncoder);
        user2.changePassword(new Password("Password1234!"), passwordEncoder);
        admin.changePassword(new Password("Password1234!"), passwordEncoder);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(admin);

        return "ok";
    }

    @GetMapping("/reviews")
    public String reviews() {
        ProductId productId = new ProductId(1L);
        Review review = Review.fake(productId);

        reviewRepository.save(review);

        return "ok";
    }
}
