package com.wemade.coupon.utils;


import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {
    public static final ZoneId ZONE_ID_SEOUL = ZoneId.of("Asia/Seoul");

    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now(ZONE_ID_SEOUL);

    }
}
