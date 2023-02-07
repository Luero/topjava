package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    public List<Meal> getAll();

    public Meal get(Integer id);

    public void delete(Integer id);

    public void update(Meal meal);

    public void create(Meal meal);
}
