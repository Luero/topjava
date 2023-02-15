package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for(Meal meal : MealsUtil.meals)
        {
            save(meal, meal.getUserId());
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        if(repository.get(meal.getId()).getUserId() != userId)
            return null;
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, exMeal) -> {meal.setUserId(userId);
        return meal;});
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.get(id).getUserId() == userId ? repository.remove(id) != null : false;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(id).getUserId() == userId ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal :: getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public List<Meal> filteredByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return getAll(userId)
                .stream()
                .filter(meal -> DateTimeUtil.isBetweenOpen(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }

    private Meal get(int id) {
        return repository.get(id);
    }
}

