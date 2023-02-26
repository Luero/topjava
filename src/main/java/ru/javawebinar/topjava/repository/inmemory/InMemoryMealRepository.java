package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

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
            save(meal, SecurityUtil.authUserId());
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> usersMeals = new HashMap<>();
            repository.computeIfAbsent(userId, meals -> usersMeals).put(meal.getId(), meal);
            return meal;
        }

        Map<Integer, Meal> usersMeals = repository.get(userId);
        Meal mealToBeUpdated = usersMeals == null ? null : usersMeals.get(meal.getId());

        if (mealToBeUpdated == null || SecurityUtil.authUserId() != userId) {
            return null;
        }

        usersMeals.computeIfPresent(meal.getId(), (id, exMeal) -> meal);
        repository.put(userId, usersMeals);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> usersMeals = repository.get(userId);
        Meal meal = usersMeals == null ? null : usersMeals.get(id);
        return meal != null && userId == SecurityUtil.authUserId() && repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> usersMeals = repository.get(userId);
        Meal meal = usersMeals == null ? null : usersMeals.get(id);
        return meal == null || userId != SecurityUtil.authUserId() ? null : meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filteredByPredicate(meal -> true, userId);
    }

    @Override
    public List<Meal> filteredByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return filteredByPredicate(meal -> DateTimeUtil.isBetweenClosed(meal.getDate(), startDate, endDate), userId);
    }

    private List<Meal> filteredByPredicate(Predicate<Meal> filter, int userId) {
        Map<Integer, Meal> usersMeals = repository.get(userId);
        if (usersMeals != null) {
            return usersMeals
                    .values()
                    .stream()
                    .filter(filter)
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}

