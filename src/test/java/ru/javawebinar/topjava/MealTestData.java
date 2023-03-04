package ru.javawebinar.topjava;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

public class MealTestData {
    public static final Meal meal1 = new Meal(START_SEQ, LocalDateTime.of(2023, Month.FEBRUARY, 16, 10, 00),
            "light breakfast", 330);
    public static final Meal meal2 = new Meal(START_SEQ++, LocalDateTime.of(2023, Month.FEBRUARY, 16, 15, 00),
            "dinner", 550);
    public static final Meal meal3 = new Meal(START_SEQ+2, LocalDateTime.of(2023, Month.FEBRUARY, 16, 17, 00),
            "coffee", 30);
    public static final Meal meal4 = new Meal(START_SEQ+3, LocalDateTime.of(2023, Month.FEBRUARY, 16, 19, 40),
            "salad and fish", 320);
    public static final Meal meal5 = new Meal(START_SEQ+4, LocalDateTime.of(2023, Month.FEBRUARY, 20, 9, 30),
            "English breakfast", 450);
    public static final Meal meal6 = new Meal(START_SEQ+5, LocalDateTime.of(2023, Month.FEBRUARY, 20, 13, 00),
            "soup with vegetables", 250);
    public static final Meal meal7 = new Meal(START_SEQ+6, LocalDateTime.of(2023, Month.FEBRUARY, 23, 11, 00),
            "panchakes with jam", 500);
    public static final Meal meal8 = new Meal(START_SEQ+7, LocalDateTime.of(2023, Month.FEBRUARY, 23, 15, 00),
            "meat with potatoes", 560);
    public static final Meal meal9 = new Meal(START_SEQ+8, LocalDateTime.of(2023, Month.FEBRUARY, 23, 18, 00),
            "fish and chips", 500);
    public static final Meal meal10 = new Meal(START_SEQ+9, LocalDateTime.of(2023, Month.FEBRUARY, 23, 20, 00),
            "beer with pizza", 600);
    public static final Meal meal11 = new Meal(START_SEQ+10, LocalDateTime.of(2023, Month.FEBRUARY, 24, 12, 00),
            "sushi with salmon", 350);
    public static final Meal meal12 = new Meal(START_SEQ+11, LocalDateTime.of(2023, Month.FEBRUARY, 24, 18, 00),
            "greek salad", 300);
    public static final int NOT_FOUND = 10;

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
