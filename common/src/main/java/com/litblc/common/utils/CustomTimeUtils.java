package com.litblc.common.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class CustomTimeUtils {

    /**
     * 转换long型时间戳 （默认国内）
     * 为不同用户以不同的偏好来显示不同的本地时间
     * @param epochMilli 时间戳
     * @return
     */
    public static String timestampToString(Long epochMilli) {
        Instant instant = Instant.ofEpochMilli(epochMilli);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return formatter2.withLocale(Locale.CHINA).format(ZonedDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai")));
    }

    /**
     * 转换long型时间戳
     * 为不同用户以不同的偏好来显示不同的本地时间
     * @param epochMilli 时间戳
     * @param locale 描述语言，如 '五月' or 'May'
     * @param zoneId 时区
     * @return
     */
    public static String timestampToString(Long epochMilli, Locale locale, String zoneId) {
        Instant instant = Instant.ofEpochMilli(epochMilli);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return formatter2.withLocale(locale).format(ZonedDateTime.ofInstant(instant, ZoneId.of(zoneId)));
    }
}
