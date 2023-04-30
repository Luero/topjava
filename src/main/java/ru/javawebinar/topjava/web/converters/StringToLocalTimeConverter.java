package ru.javawebinar.topjava.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalTime;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(@Nullable String timeString) {
        if (timeString.isEmpty()) {
            return null;
        }
        return LocalTime.parse(timeString);
    }
}
