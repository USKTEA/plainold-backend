package com.usktea.plainold.backdoor;

import com.usktea.plainold.models.option.Option;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Password;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.OptionRepository;
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

    public BackdoorController(OptionRepository optionRepository, UserRepository userRepository) {
        this.optionRepository = optionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/option")
    public String option() {
        ProductId productId = new ProductId(1L);

        Option option = Option.fake(productId);

        option.addOptions();

        optionRepository.save(option);

        return "ok";
    }

    @GetMapping("/user")
    public String user() {
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        Users user = Users.fake(new Username("tjrxo1234@gmail.com"));

        user.changePassword(new Password("Password1234!"), passwordEncoder);

        userRepository.save(user);

        return "ok";
    }
}
