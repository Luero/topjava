package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.meals) {
            save(meal, meal.getUserId());
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.computeIfAbsent(userId, meals -> new HashMap<>()).put(meal.getId(), meal);
            return meal;
        }

        Meal mealToBeUpdated = repository.get(userId).get(meal.getId());
        if (mealToBeUpdated == null) {
            return null;
        }

        if (mealToBeUpdated.getUserId() != userId) {
            return null;
        }

        return repository.get(userId).computeIfPresent(meal.getId(), (id, exMeal) -> {
            meal.setUserId(userId);
            return meal;
        });
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(userId).get(id);
        if(meal == null) {
            return false;
        } else {
            return meal.getUserId() == userId && repository.remove(id) != null;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(userId).get(id);
        if(meal == null) {
            return null;
        } else {
            return meal.getUserId() == userId ? meal : null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filteredByPredicate(Objects::nonNull, userId);
    }

    public List<Meal> filteredByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return filteredByPredicate(meal -> DateTimeUtil.isBetweenClosed(meal.getDate(), startDate, endDate), userId);
    }

    private List<Meal> filteredByPredicate(Predicate<Meal> filter, int userId) {
        return repository.get(userId)
                .values()
                .stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

