package ru.javawebinar.topjava.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(@Nullable String dateString) {
        if (dateString.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateString);
    }
}
