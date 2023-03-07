package ru.javawebinar.topjava;

import static org.assertj.core.api.Assertions.assertThat;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final Meal userMeal1 = new Meal(START_SEQ+3, LocalDateTime.of(2023, Month.FEBRUARY, 16, 10, 0),
            "light breakfast", 330);
    public static final Meal userMeal2 = new Meal(START_SEQ+4, LocalDateTime.of(2023, Month.FEBRUARY, 16, 15, 0),
            "dinner", 550);
    public static final Meal userMeal3 = new Meal(START_SEQ+5, LocalDateTime.of(2023, Month.FEBRUARY, 16, 17, 0),
            "coffee", 30);
    public static final Meal userMeal4 = new Meal(START_SEQ+6, LocalDateTime.of(2023, Month.FEBRUARY, 16, 19, 40),
            "salad and fish", 320);
    public static final Meal uderMeal5 = new Meal(START_SEQ+7, LocalDateTime.of(2023, Month.FEBRUARY, 20, 9, 30),
            "English breakfast", 450);
    public static final Meal userMeal6 = new Meal(START_SEQ+8, LocalDateTime.of(2023, Month.FEBRUARY, 20, 13, 0),
            "soup with vegetables", 250);
    public static final Meal userMeal7 = new Meal(START_SEQ+9, LocalDateTime.of(2023, Month.MARCH, 5, 0, 0),
            "a glass of wine", 350);
    public static final Meal adminMeal1 = new Meal(START_SEQ+10, LocalDateTime.of(2023, Month.FEBRUARY, 23, 11, 0),
            "panchakes with jam", 500);
    public static final Meal adminMeal2 = new Meal(START_SEQ+11, LocalDateTime.of(2023, Month.FEBRUARY, 23, 15, 0),
            "meat with potatoes", 560);
    public static final Meal adminMeal3 = new Meal(START_SEQ+12, LocalDateTime.of(2023, Month.FEBRUARY, 23, 18, 0),
            "fish and chips", 500);
    public static final Meal adminMeal4 = new Meal(START_SEQ+13, LocalDateTime.of(2023, Month.FEBRUARY, 23, 20, 0),
            "beer with pizza", 600);
    public static final Meal adminMeal5 = new Meal(START_SEQ+14, LocalDateTime.of(2023, Month.FEBRUARY, 24, 12, 0),
            "sushi with salmon", 350);
    public static final Meal adminMeal6 = new Meal(START_SEQ+15, LocalDateTime.of(2023, Month.FEBRUARY, 24, 18, 0),
            "greek salad", 300);
    public static final int NOT_FOUND = 10;

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2023, Month.FEBRUARY, 21, 10, 0, 0), "New meal", 1000);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
