package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    List<Meal> getAll();

    Meal get(int id);

    void delete(int id);

    void update(Meal meal);

    void create(Meal meal);
}
