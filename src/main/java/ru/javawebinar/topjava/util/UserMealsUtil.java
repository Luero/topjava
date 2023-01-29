package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //returns filtered list with excess. Implementation by cycles

        Map<LocalDate, Integer> dailyCalories = new HashMap<>();

        for (UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            int calories = meal.getCalories();

            if (dailyCalories.containsKey(date)) {
                int caloriesValue = dailyCalories.get(date) + calories;
                dailyCalories.put(date, caloriesValue);
            } else {
                dailyCalories.put(date, calories);
            }
        }

        List<UserMeal> mealsInRange = new ArrayList<>();

        for (UserMeal meal : meals) {
            LocalTime time = meal.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenHalfOpen(time, startTime, endTime)) {
                mealsInRange.add(meal);
            }
        }

        List<UserMealWithExcess> result = new ArrayList<>();

        for (UserMeal meal : mealsInRange) {
            LocalDate date = meal.getDateTime().toLocalDate();
            int caloriesPerDate = dailyCalories.getOrDefault(date, caloriesPerDay);
            if (caloriesPerDate > caloriesPerDay) {
                result.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));
            }
        }

        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Implementation by streams

        List<UserMeal> mealsInRange = meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());

        Map<LocalDate, Integer> dailyCalories = meals
                .stream()
                .collect(Collectors.groupingBy(UserMeal::getLocalDate,
                        Collectors.summingInt(UserMeal::getCalories)));

        List<LocalDate> datesWithExcess = dailyCalories.entrySet()
                .stream()
                .filter(x -> x.getValue() > caloriesPerDay)
                .map(x -> x.getKey())
                .collect(Collectors.toList());


        List<UserMealWithExcess> result = mealsInRange.stream()
                .filter(x -> datesWithExcess.contains(x.getLocalDate()))
                .map(x -> new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(), true))
                .collect(Collectors.toList());

        return result;
    }


}
