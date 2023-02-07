package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoImpl implements MealDao {
    private AtomicInteger counter = new AtomicInteger();
    private final Map<Integer, Meal> inMemoryCollection = new ConcurrentHashMap<>();

    {
    inMemoryCollection.put(counter.incrementAndGet(), new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
    inMemoryCollection.put(counter.incrementAndGet(), new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
    inMemoryCollection.put(counter.incrementAndGet(), new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
    inMemoryCollection.put(counter.incrementAndGet(), new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
    inMemoryCollection.put(counter.incrementAndGet(), new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
    inMemoryCollection.put(counter.incrementAndGet(), new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
    inMemoryCollection.put(counter.incrementAndGet(), new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    private Integer getId() {
        return counter.get();
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(inMemoryCollection.values());
    }

    @Override
    public Meal get(Integer id) {
        return inMemoryCollection.get(id);
    }

    @Override
    public void delete(Integer id) {
        inMemoryCollection.remove(id);
    }

    @Override
    public void update(Meal meal) {
        Meal mealToBeUpdated = get(meal.getId());
        mealToBeUpdated.setCalories(meal.getCalories());
        mealToBeUpdated.setDescription(meal.getDescription());
        mealToBeUpdated.setDateTime(meal.getDateTime());
        inMemoryCollection.put(meal.getId(), mealToBeUpdated);
    }

    @Override
    public void create(Meal meal) {
        Integer id = getId();
        meal.setId(id);
        inMemoryCollection.put(meal.getId(), meal);
    }

    public String print()
    {
        List<Meal> list = getAll();
        StringBuilder builder = new StringBuilder();
        for(Meal meal : list)
        {
            builder.append(meal.toString());
        }
        return builder.toString();
    }
}
