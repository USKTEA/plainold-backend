package com.usktea.plainold.applications;

import com.usktea.plainold.models.OrderNumber;
import com.usktea.plainold.models.UserName;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class OrderNumberService {
    public OrderNumber nextOrderNumber(UserName userName) {
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

    private String userInfo(UserName userName) {
        return userName.beforeAt();
    }
}
