package com.usktea.plainold.backdoor;

import com.usktea.plainold.models.option.Option;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.repositories.OptionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("backdoor")
public class BackdoorController {
    private OptionRepository optionRepository;

    public BackdoorController(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @GetMapping
    public void option() {
        ProductId productId = new ProductId(1L);

        Option option = Option.fake(productId);

        option.addOptions();

        optionRepository.save(option);
    }
}
