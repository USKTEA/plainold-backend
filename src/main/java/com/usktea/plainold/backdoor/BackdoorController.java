package com.usktea.plainold.backdoor;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.usktea.plainold.models.category.Category;
import com.usktea.plainold.models.category.CategoryId;
import com.usktea.plainold.models.common.Money;
import com.usktea.plainold.models.option.Option;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.product.Description;
import com.usktea.plainold.models.product.Detail;
import com.usktea.plainold.models.product.Image;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.product.ProductImageUrl;
import com.usktea.plainold.models.product.ProductName;
import com.usktea.plainold.models.product.ProductStatus;
import com.usktea.plainold.models.product.Shipping;
import com.usktea.plainold.models.product.ShippingMethod;
import com.usktea.plainold.models.product.Summary;
import com.usktea.plainold.models.product.ThumbnailUrl;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Password;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.CategoryRepository;
import com.usktea.plainold.repositories.OptionRepository;
import com.usktea.plainold.repositories.OrderRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("backdoor")
public class BackdoorController {
    private OptionRepository optionRepository;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public BackdoorController(OptionRepository optionRepository,
                              UserRepository userRepository,
                              ReviewRepository reviewRepository,
                              ProductRepository productRepository,
                              OrderRepository orderRepository,
                              CategoryRepository categoryRepository
    ) {
        this.optionRepository = optionRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/option")
    public String option() {
        for (Long i = 0L; i < 50L; i += 2L) {
            ProductId productId = new ProductId(i);

            Option option = Option.fake(productId);

            option.addOptions();

            optionRepository.save(option);
        }

        return "ok";
    }

    @GetMapping("/users")
    public String user() {
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        Users user1 = Users.fake(new Username("tjrxo1234@gmail.com"));
        Users user2 = Users.fake(new Username("rlatjrxo1234@gmail.com"));
        Users user3 = Users.fake(new Username("test@gmail.com"));

        Users admin = Users.fake(Role.ADMIN);

        user1.changePassword(new Password("Password1234!"), passwordEncoder);
        user2.changePassword(new Password("Password1234!"), passwordEncoder);
        user3.changePassword(new Password("Password1234!"), passwordEncoder);
        admin.changePassword(new Password("Password1234!"), passwordEncoder);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(admin);

        return "ok";
    }

    @GetMapping("/reviews")
    public String reviews() {
        for (Long i = 0L; i < 55L; i += 1L) {
            ProductId productId = new ProductId(i);
            Review review = Review.fake(productId);

            reviewRepository.save(review);
        }

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

    @GetMapping("/products")
    @Transactional
    public String products() {
        productRepository.deleteAll();

//        StringBuilder builder = new StringBuilder();
//
//        List<Product> products = productRepository.findAll();
//
//        products.forEach((product -> {
//            builder.append(
//                    product.name().getValue() + "!!" +
//                    product.categoryId().value() + "!!" +
//                    product.price().getAmount() + "!!" +
//                    product.image().thumbnail() + "!!" +
//                    product.image().productImageUrls().toArray(new ProductImageUrl[]{})[0].productImageUrl() + "!!" +
//                    product.image().productImageUrls().toArray(new ProductImageUrl[]{})[1].productImageUrl() + "!!" +
//                    product.image().productImageUrls().toArray(new ProductImageUrl[]{})[2].productImageUrl() + "!!" +
//                    product.description().summary().content() + "!!" +
//                    product.description().detail().content() + "@@");
//        }));

        String data = "BASIC T-SHIRT!!1!!104000!!https://plainold.s3.amazonaws.com/product-image/1.jpg!!https://plainold.s3.amazonaws.com/product-image/1.jpg!!https://plainold.s3.amazonaws.com/product-image/2.jpg!!https://plainold.s3.amazonaws.com/product-image/3.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@YALE HOODIE!!1!!28000!!https://plainold.s3.amazonaws.com/product-image/4.jpg!!https://plainold.s3.amazonaws.com/product-image/5.jpg!!https://plainold.s3.amazonaws.com/product-image/6.jpg!!https://plainold.s3.amazonaws.com/product-image/4.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@BASIC HOODIE!!1!!37000!!https://plainold.s3.amazonaws.com/product-image/7.jpg!!https://plainold.s3.amazonaws.com/product-image/9.jpg!!https://plainold.s3.amazonaws.com/product-image/8.jpg!!https://plainold.s3.amazonaws.com/product-image/7.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@CAPMAN HOODIE!!1!!63000!!https://plainold.s3.amazonaws.com/product-image/10.jpg!!https://plainold.s3.amazonaws.com/product-image/10.jpg!!https://plainold.s3.amazonaws.com/product-image/12.jpg!!https://plainold.s3.amazonaws.com/product-image/11.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@PHYPS HOODIE!!1!!1000!!https://plainold.s3.amazonaws.com/product-image/13.jpg!!https://plainold.s3.amazonaws.com/product-image/15.jpg!!https://plainold.s3.amazonaws.com/product-image/14.jpg!!https://plainold.s3.amazonaws.com/product-image/13.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@THISISTHAT T-SHIRT!!1!!80000!!https://plainold.s3.amazonaws.com/product-image/16.jpg!!https://plainold.s3.amazonaws.com/product-image/18.jpg!!https://plainold.s3.amazonaws.com/product-image/17.jpg!!https://plainold.s3.amazonaws.com/product-image/16.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@SMILE FLOWER T-SHIRT!!1!!102000!!https://plainold.s3.amazonaws.com/product-image/19.jpg!!https://plainold.s3.amazonaws.com/product-image/21.jpg!!https://plainold.s3.amazonaws.com/product-image/19.jpg!!https://plainold.s3.amazonaws.com/product-image/20.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@ILLU SWEATSHIRT!!1!!89000!!https://plainold.s3.amazonaws.com/product-image/22.jpg!!https://plainold.s3.amazonaws.com/product-image/22.jpg!!https://plainold.s3.amazonaws.com/product-image/24.jpg!!https://plainold.s3.amazonaws.com/product-image/23.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@YALE SWEATSHIRT!!1!!30000!!https://plainold.s3.amazonaws.com/product-image/25.jpg!!https://plainold.s3.amazonaws.com/product-image/25.jpg!!https://plainold.s3.amazonaws.com/product-image/26.jpg!!https://plainold.s3.amazonaws.com/product-image/27.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@BASIC PK-SHIRT!!1!!10000!!https://plainold.s3.amazonaws.com/product-image/28.jpg!!https://plainold.s3.amazonaws.com/product-image/30.jpg!!https://plainold.s3.amazonaws.com/product-image/29.jpg!!https://plainold.s3.amazonaws.com/product-image/28.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@STRIPE PK-SHIRT!!1!!109000!!https://plainold.s3.amazonaws.com/product-image/31.jpg!!https://plainold.s3.amazonaws.com/product-image/31.jpg!!https://plainold.s3.amazonaws.com/product-image/33.jpg!!https://plainold.s3.amazonaws.com/product-image/32.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@CAPMAP PK-SHIRT!!1!!150000!!https://plainold.s3.amazonaws.com/product-image/34.jpg!!https://plainold.s3.amazonaws.com/product-image/35.jpg!!https://plainold.s3.amazonaws.com/product-image/34.jpg!!https://plainold.s3.amazonaws.com/product-image/36.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@LONG SLEEVE PK-SHIRT!!1!!89000!!https://plainold.s3.amazonaws.com/product-image/37.jpg!!https://plainold.s3.amazonaws.com/product-image/39.jpg!!https://plainold.s3.amazonaws.com/product-image/38.jpg!!https://plainold.s3.amazonaws.com/product-image/37.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@LONG SLEEVE CAPMAN PK-SHIRT!!1!!116000!!https://plainold.s3.amazonaws.com/product-image/40.jpg!!https://plainold.s3.amazonaws.com/product-image/40.jpg!!https://plainold.s3.amazonaws.com/product-image/42.jpg!!https://plainold.s3.amazonaws.com/product-image/41.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@EBFD SWEATSHIRT!!1!!103000!!https://plainold.s3.amazonaws.com/product-image/43.jpg!!https://plainold.s3.amazonaws.com/product-image/45.jpg!!https://plainold.s3.amazonaws.com/product-image/44.jpg!!https://plainold.s3.amazonaws.com/product-image/43.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@LEE BASIC SWEATSHIRT!!1!!49000!!https://plainold.s3.amazonaws.com/product-image/46.jpg!!https://plainold.s3.amazonaws.com/product-image/46.jpg!!https://plainold.s3.amazonaws.com/product-image/47.jpg!!https://plainold.s3.amazonaws.com/product-image/48.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@FLOWER SWEATSHIRT!!1!!109000!!https://plainold.s3.amazonaws.com/product-image/49.jpg!!https://plainold.s3.amazonaws.com/product-image/49.jpg!!https://plainold.s3.amazonaws.com/product-image/50.jpg!!https://plainold.s3.amazonaws.com/product-image/51.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@JEEP BUTTER TOP!!1!!148000!!https://plainold.s3.amazonaws.com/product-image/52.jpg!!https://plainold.s3.amazonaws.com/product-image/54.jpg!!https://plainold.s3.amazonaws.com/product-image/53.jpg!!https://plainold.s3.amazonaws.com/product-image/52.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@SHORT KNEET SWEATSHIRT!!1!!13000!!https://plainold.s3.amazonaws.com/product-image/55.jpg!!https://plainold.s3.amazonaws.com/product-image/57.jpg!!https://plainold.s3.amazonaws.com/product-image/55.jpg!!https://plainold.s3.amazonaws.com/product-image/56.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@YALE GREY SWEATSHIRT!!1!!27000!!https://plainold.s3.amazonaws.com/product-image/58.jpg!!https://plainold.s3.amazonaws.com/product-image/58.jpg!!https://plainold.s3.amazonaws.com/product-image/59.jpg!!https://plainold.s3.amazonaws.com/product-image/60.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@BIGDOG SWEATSHIRT!!1!!94000!!https://plainold.s3.amazonaws.com/product-image/61.jpg!!https://plainold.s3.amazonaws.com/product-image/63.jpg!!https://plainold.s3.amazonaws.com/product-image/61.jpg!!https://plainold.s3.amazonaws.com/product-image/62.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@GREEN BROCOLLI SWEATSHIRT!!1!!114000!!https://plainold.s3.amazonaws.com/product-image/64.jpg!!https://plainold.s3.amazonaws.com/product-image/64.jpg!!https://plainold.s3.amazonaws.com/product-image/65.jpg!!https://plainold.s3.amazonaws.com/product-image/66.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@ORIGINAL SWEATSHIRT!!1!!21000!!https://plainold.s3.amazonaws.com/product-image/67.jpg!!https://plainold.s3.amazonaws.com/product-image/69.jpg!!https://plainold.s3.amazonaws.com/product-image/68.jpg!!https://plainold.s3.amazonaws.com/product-image/67.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@CUTE CAT SWEATSHIRT!!1!!140000!!https://plainold.s3.amazonaws.com/product-image/70.jpg!!https://plainold.s3.amazonaws.com/product-image/70.jpg!!https://plainold.s3.amazonaws.com/product-image/71.jpg!!https://plainold.s3.amazonaws.com/product-image/72.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@GOOD DOG SWEATSHIRT!!1!!75000!!https://plainold.s3.amazonaws.com/product-image/73.jpg!!https://plainold.s3.amazonaws.com/product-image/75.jpg!!https://plainold.s3.amazonaws.com/product-image/74.jpg!!https://plainold.s3.amazonaws.com/product-image/73.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@WEED TWEED SHIRT!!1!!114000!!https://plainold.s3.amazonaws.com/product-image/76.jpg!!https://plainold.s3.amazonaws.com/product-image/76.jpg!!https://plainold.s3.amazonaws.com/product-image/78.jpg!!https://plainold.s3.amazonaws.com/product-image/77.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@BROWN TWEED SHIRT!!1!!53000!!https://plainold.s3.amazonaws.com/product-image/79.jpg!!https://plainold.s3.amazonaws.com/product-image/80.jpg!!https://plainold.s3.amazonaws.com/product-image/81.jpg!!https://plainold.s3.amazonaws.com/product-image/79.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@COVERNAT SWEATSHIRT!!1!!89000!!https://plainold.s3.amazonaws.com/product-image/82.jpg!!https://plainold.s3.amazonaws.com/product-image/83.jpg!!https://plainold.s3.amazonaws.com/product-image/82.jpg!!https://plainold.s3.amazonaws.com/product-image/84.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@BROOK BROTHER PK-TEE!!1!!133000!!https://plainold.s3.amazonaws.com/product-image/85.jpg!!https://plainold.s3.amazonaws.com/product-image/87.jpg!!https://plainold.s3.amazonaws.com/product-image/86.jpg!!https://plainold.s3.amazonaws.com/product-image/85.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@BLUE CROP SWEATER!!1!!56000!!https://plainold.s3.amazonaws.com/product-image/88.jpg!!https://plainold.s3.amazonaws.com/product-image/88.jpg!!https://plainold.s3.amazonaws.com/product-image/89.jpg!!https://plainold.s3.amazonaws.com/product-image/90.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@YALE HALF SWEATER!!1!!136000!!https://plainold.s3.amazonaws.com/product-image/91.jpg!!https://plainold.s3.amazonaws.com/product-image/93.jpg!!https://plainold.s3.amazonaws.com/product-image/91.jpg!!https://plainold.s3.amazonaws.com/product-image/92.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@COVERNAT TUTLE NECK!!1!!97000!!https://plainold.s3.amazonaws.com/product-image/94.jpg!!https://plainold.s3.amazonaws.com/product-image/94.jpg!!https://plainold.s3.amazonaws.com/product-image/95.jpg!!https://plainold.s3.amazonaws.com/product-image/96.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@1985 HOODIE!!1!!133000!!https://plainold.s3.amazonaws.com/product-image/97.jpg!!https://plainold.s3.amazonaws.com/product-image/98.jpg!!https://plainold.s3.amazonaws.com/product-image/99.jpg!!https://plainold.s3.amazonaws.com/product-image/97.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@PHYSICAL HOODIE!!1!!94000!!https://plainold.s3.amazonaws.com/product-image/100.jpg!!https://plainold.s3.amazonaws.com/product-image/100.jpg!!https://plainold.s3.amazonaws.com/product-image/101.jpg!!https://plainold.s3.amazonaws.com/product-image/102.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@DDONATHAT HOODIE!!1!!60000!!https://plainold.s3.amazonaws.com/product-image/103.jpg!!https://plainold.s3.amazonaws.com/product-image/103.jpg!!https://plainold.s3.amazonaws.com/product-image/104.jpg!!https://plainold.s3.amazonaws.com/product-image/105.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@NOMANUAL HOODIE!!1!!66000!!https://plainold.s3.amazonaws.com/product-image/106.jpg!!https://plainold.s3.amazonaws.com/product-image/108.jpg!!https://plainold.s3.amazonaws.com/product-image/107.jpg!!https://plainold.s3.amazonaws.com/product-image/106.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@YES EYE SEE HOODIE!!1!!63000!!https://plainold.s3.amazonaws.com/product-image/109.jpg!!https://plainold.s3.amazonaws.com/product-image/111.jpg!!https://plainold.s3.amazonaws.com/product-image/110.jpg!!https://plainold.s3.amazonaws.com/product-image/109.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@1987 BLUE HOODIE!!1!!71000!!https://plainold.s3.amazonaws.com/product-image/112.jpg!!https://plainold.s3.amazonaws.com/product-image/114.jpg!!https://plainold.s3.amazonaws.com/product-image/113.jpg!!https://plainold.s3.amazonaws.com/product-image/112.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@SMILE GRAY HOODIE!!1!!39000!!https://plainold.s3.amazonaws.com/product-image/115.jpg!!https://plainold.s3.amazonaws.com/product-image/117.jpg!!https://plainold.s3.amazonaws.com/product-image/115.jpg!!https://plainold.s3.amazonaws.com/product-image/116.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@TRILLION 2-TONE HOODIE!!1!!135000!!https://plainold.s3.amazonaws.com/product-image/118.jpg!!https://plainold.s3.amazonaws.com/product-image/118.jpg!!https://plainold.s3.amazonaws.com/product-image/120.jpg!!https://plainold.s3.amazonaws.com/product-image/119.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@STRAINT BLUE JEAN!!2!!33000!!https://plainold.s3.ap-northeast-2.amazonaws.com/bottom-image/200.jpg!!https://plainold.s3.ap-northeast-2.amazonaws.com/bottom-image/202.jpg!!https://plainold.s3.ap-northeast-2.amazonaws.com/bottom-image/201.jpg!!https://plainold.s3.ap-northeast-2.amazonaws.com/bottom-image/200.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@EIGTH SIX JEAN!!2!!58000!!https://plainold.s3.amazonaws.com/bottom-image/124.jpg!!https://plainold.s3.amazonaws.com/bottom-image/125.jpg!!https://plainold.s3.amazonaws.com/bottom-image/126.jpg!!https://plainold.s3.amazonaws.com/bottom-image/124.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@BLACK MONAMI!!2!!79000!!https://plainold.s3.amazonaws.com/bottom-image/127.jpg!!https://plainold.s3.amazonaws.com/bottom-image/129.jpg!!https://plainold.s3.amazonaws.com/bottom-image/128.jpg!!https://plainold.s3.amazonaws.com/bottom-image/127.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@WIDE COTTON PANTS!!2!!102000!!https://plainold.s3.amazonaws.com/bottom-image/130.jpg!!https://plainold.s3.amazonaws.com/bottom-image/131.jpg!!https://plainold.s3.amazonaws.com/bottom-image/132.jpg!!https://plainold.s3.amazonaws.com/bottom-image/130.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@GRAY TRAINING PANTS!!2!!111000!!https://plainold.s3.amazonaws.com/bottom-image/133.jpg!!https://plainold.s3.amazonaws.com/bottom-image/134.jpg!!https://plainold.s3.amazonaws.com/bottom-image/133.jpg!!https://plainold.s3.amazonaws.com/bottom-image/135.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@PINK COLLAR JACKET!!3!!136000!!https://plainold.s3.amazonaws.com/bottom-image/136.jpg!!https://plainold.s3.amazonaws.com/bottom-image/138.jpg!!https://plainold.s3.amazonaws.com/bottom-image/136.jpg!!https://plainold.s3.amazonaws.com/bottom-image/137.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@GONZAL GRAY JACKET!!3!!7000!!https://plainold.s3.amazonaws.com/bottom-image/139.jpg!!https://plainold.s3.amazonaws.com/bottom-image/141.jpg!!https://plainold.s3.amazonaws.com/bottom-image/139.jpg!!https://plainold.s3.amazonaws.com/bottom-image/140.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@IVORY CHECKED JACKET!!3!!59000!!https://plainold.s3.amazonaws.com/bottom-image/142.jpg!!https://plainold.s3.amazonaws.com/bottom-image/143.jpg!!https://plainold.s3.amazonaws.com/bottom-image/144.jpg!!https://plainold.s3.amazonaws.com/bottom-image/142.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@THISNATHAT BLACK JACKET!!3!!124000!!https://plainold.s3.amazonaws.com/bottom-image/145.jpg!!https://plainold.s3.amazonaws.com/bottom-image/145.jpg!!https://plainold.s3.amazonaws.com/bottom-image/146.jpg!!https://plainold.s3.amazonaws.com/bottom-image/147.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@SHINE LEATHER JACKET!!3!!74000!!https://plainold.s3.amazonaws.com/bottom-image/148.jpg!!https://plainold.s3.amazonaws.com/bottom-image/149.jpg!!https://plainold.s3.amazonaws.com/bottom-image/150.jpg!!https://plainold.s3.amazonaws.com/bottom-image/148.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@WHITE TRANING SHOE!!4!!119000!!https://plainold.s3.amazonaws.com/bottom-image/151.jpg!!https://plainold.s3.amazonaws.com/bottom-image/153.jpg!!https://plainold.s3.amazonaws.com/bottom-image/152.jpg!!https://plainold.s3.amazonaws.com/bottom-image/151.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@WHITE TENNIS SHOE!!4!!113000!!https://plainold.s3.amazonaws.com/bottom-image/154.jpg!!https://plainold.s3.amazonaws.com/bottom-image/155.jpg!!https://plainold.s3.amazonaws.com/bottom-image/154.jpg!!https://plainold.s3.amazonaws.com/bottom-image/156.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@VANS BLACK LEGACY!!4!!79000!!https://plainold.s3.amazonaws.com/bottom-image/157.jpg!!https://plainold.s3.amazonaws.com/bottom-image/159.jpg!!https://plainold.s3.amazonaws.com/bottom-image/157.jpg!!https://plainold.s3.amazonaws.com/bottom-image/158.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@VANS RED SKATESHOE!!4!!70000!!https://plainold.s3.amazonaws.com/bottom-image/160.jpg!!https://plainold.s3.amazonaws.com/bottom-image/160.jpg!!https://plainold.s3.amazonaws.com/bottom-image/161.jpg!!https://plainold.s3.amazonaws.com/bottom-image/162.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@Dr. MARTION BLACK SHOE!!4!!72000!!https://plainold.s3.amazonaws.com/bottom-image/163.jpg!!https://plainold.s3.amazonaws.com/bottom-image/164.jpg!!https://plainold.s3.amazonaws.com/bottom-image/165.jpg!!https://plainold.s3.amazonaws.com/bottom-image/163.jpg!!Lorem ipsum dolor sit amet!!consectetur adipiscing elit, sed do@@";

        String[] productData = data.split("@@");

        for (int i = 0; i < productData.length; i += 1) {
            String[] values = productData[i].split("!!");

            Product product = new Product(
                    new ProductId(Long.valueOf(i + 1)),
                    new Money(Long.parseLong(values[2])),
                    new ProductName(values[0]),
                    new CategoryId(Long.parseLong(values[1])),
                    new Image(
                            new ThumbnailUrl(values[3]),
                            Set.of(
                                    new ProductImageUrl(values[4]),
                                    new ProductImageUrl(values[5]),
                                    new ProductImageUrl(values[6])
                            )
                    ),
                    new Description(
                            new Summary(values[7]),
                            new Detail(values[8])
                    ),
                    Shipping.fake(ShippingMethod.Parcel),
                    ProductStatus.ON_SALE,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            productRepository.save(product);

        }

        return "ok";
    }

    @GetMapping("/categories")
    @Transactional
    public String categories() {
        Category top = new Category(1L, "Top");
        Category bottom = new Category(2L, "Bottom");
        Category outers = new Category(3L, "Outers");
        Category shoes = new Category(4L, "Shoes");

        categoryRepository.save(top);
        categoryRepository.save(bottom);
        categoryRepository.save(outers);
        categoryRepository.save(shoes);

        return "ok";
    }

    @GetMapping("/orders")
    @Transactional
    public String orders() {
        List<Order> orders = orderRepository.findAll();

        orders.forEach((order) ->
                order.markDeliveryComplete()
        );

        return "ok";
    }

    @GetMapping("/money")
    @Transactional
    public String money() {
        Users user = userRepository.findByUsername(new Username("test@gmail.com")).get();

        user.addPurchaseAmount(new Money(1247000L));

        return "ok";
    }
}
