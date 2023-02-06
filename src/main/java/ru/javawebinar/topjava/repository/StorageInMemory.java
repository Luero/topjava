package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StorageInMemory {
    public static final int CALORIES_PER_DAY = 2000;
    private static AtomicInteger counter = new AtomicInteger();
    public static Map<Integer, Meal> testData = new ConcurrentHashMap<>();

    static {
        testData.put(counter.incrementAndGet(), new Meal(setId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        testData.put(counter.incrementAndGet(), new Meal(setId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        testData.put(counter.incrementAndGet(), new Meal(setId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        testData.put(counter.incrementAndGet(), new Meal(setId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        testData.put(counter.incrementAndGet(), new Meal(setId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        testData.put(counter.incrementAndGet(), new Meal(setId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        testData.put(counter.incrementAndGet(), new Meal(setId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    private static Integer setId() {
        return counter.get();
    }

    public static List<Meal> transferMapToList() {
        return new ArrayList<>(testData.values());
    }
}
