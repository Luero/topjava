package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private String datePattern = "yyyy-MM-dd";

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public LocalDate convert(String dateString) {
        if (!dateString.equals("")) {
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(datePattern));
        } else {
            return null;
        }
    }
}
