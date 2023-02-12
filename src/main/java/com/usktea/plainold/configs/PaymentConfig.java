package com.usktea.plainold.configs;

import com.usktea.plainold.applications.payment.CalculateOrderAmountService;
import com.usktea.plainold.applications.payment.KakaoPaymentService;
import com.usktea.plainold.applications.payment.PaymentService;
import com.usktea.plainold.applications.payment.PaymentServiceFactory;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.properties.KakaoPayProperties;
import com.usktea.plainold.sessions.TidSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaymentConfig {
    @Autowired
    private CalculateOrderAmountService calculateOrderAmountService;

    @Autowired
    private GetUserService getUserService;

    @Bean
    public PaymentServiceFactory paymentServiceFactory() {
        Map<String, PaymentService> services = new HashMap<>();

        services.put("KAKAOPAY", new KakaoPaymentService(
                kakaoPayProperties(), tidSession(), getUserService, calculateOrderAmountService));

        return new PaymentServiceFactory(services);
    }

    @Bean
    public KakaoPayProperties kakaoPayProperties() {
        return new KakaoPayProperties();
    }

    @Bean
    public TidSession tidSession() {
        return new TidSession();
    }
}
