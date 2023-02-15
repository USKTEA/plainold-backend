package com.usktea.plainold.backdoor;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.usktea.plainold.models.common.Money;
import com.usktea.plainold.models.option.Option;
import com.usktea.plainold.models.product.Description;
import com.usktea.plainold.models.product.Detail;
import com.usktea.plainold.models.product.Image;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.product.ProductImageUrl;
import com.usktea.plainold.models.product.Summary;
import com.usktea.plainold.models.product.ThumbnailUrl;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Password;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.OptionRepository;
import com.usktea.plainold.repositories.ProductRepository;
import com.usktea.plainold.repositories.ReviewRepository;
import com.usktea.plainold.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@RestController
@RequestMapping("backdoor")
public class BackdoorController {
    private OptionRepository optionRepository;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public BackdoorController(OptionRepository optionRepository,
                              UserRepository userRepository,
                              ReviewRepository reviewRepository,
                              ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
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

    @GetMapping("/s3")
    public String getAllUrls() {
        StringBuilder builder = new StringBuilder();

        ObjectListing objectListing = amazonS3.listObjects(bucketName);

        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            String key = objectSummary.getKey();
            if (key.startsWith("bottom-image/")) {
                builder.append(String.format("https://%s.s3.amazonaws.com/%s", bucketName, key));
                builder.append(System.lineSeparator());
            }
        }

        return builder.toString();
    }
}
