package com.dxjunkyard.community.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日付と時刻の変換ユーティリティクラス
 */
public class DateTimeUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * LocalDateTimeから曜日を取得（0=月曜日, 6=日曜日）
     * @param dateTime 日時
     * @return 曜日（0-6）
     */
    public static int getDayOfWeek(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return (dayOfWeek.getValue() - 1) % 7;
    }

    /**
     * 日付文字列から曜日を取得（0=月曜日, 6=日曜日）
     * @param dateStr 日付文字列（yyyy-MM-dd形式）
     * @return 曜日（0-6）
     */
    public static int getDayOfWeek(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return (dayOfWeek.getValue() - 1) % 7;
    }

    /**
     * LocalDateTimeから日付文字列を取得
     * @param dateTime 日時
     * @return 日付文字列（yyyy-MM-dd形式）
     */
    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }

    /**
     * LocalDateTimeから時刻文字列を取得
     * @param dateTime 日時
     * @return 時刻文字列（HH:mm形式）
     */
    public static String formatTime(LocalDateTime dateTime) {
        return dateTime.format(TIME_FORMATTER);
    }

    /**
     * 文字列からLocalDateTimeに変換
     * @param dateStr 日付文字列（yyyy-MM-dd形式）
     * @param timeStr 時刻文字列（HH:mm形式）
     * @return LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateStr, String timeStr) {
        return LocalDateTime.parse(dateStr + " " + timeStr, DATE_TIME_FORMATTER);
    }

    /**
     * 文字列からLocalDateTimeに変換
     * @param dateTimeStr 日時文字列（yyyy-MM-dd HH:mm形式）
     * @return LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
    }
}
