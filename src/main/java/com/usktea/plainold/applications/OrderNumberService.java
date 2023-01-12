package com.usktea.plainold.applications;

import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.user.Username;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class OrderNumberService {
    public OrderNumber nextOrderNumber(Username userName) {
        if (userName == null) {
            throw new IllegalArgumentException("Invalid userName:" + userName);
        }

        return new OrderNumber(userInfo(userName) + "-" + time());
    }

    private String time() {
        Date current = Calendar.getInstance().getTime();

        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmm");

        return date.format(current);
    }

    private String userInfo(Username userName) {
        return userName.beforeAt();
    }
}
