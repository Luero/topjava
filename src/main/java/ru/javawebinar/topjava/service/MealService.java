package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        Meal newMeal = repository.save(meal, userId);
        if(newMeal == null) {
            throw new NotFoundException("Not authorised");
        }
        else {
            return newMeal;
        }
    }

    public void delete(int id, int userId) {
        boolean check = repository.delete(id, userId);
        checkNotFoundWithId(check, id);
        if(!check) {
            throw new NotFoundException("Not authorised");
        }
    }

    public Meal get(int id, int userId) {
        Meal askedMeal = repository.get(id, userId);
        checkNotFoundWithId(askedMeal, id);
        if(askedMeal == null) {
            throw new NotFoundException("Not authorised");
        }
        else {
            return askedMeal;
        }
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        Meal newMeal = repository.save(meal, userId);
        checkNotFoundWithId(newMeal, meal.getId());
        if(newMeal == null) {
            throw new NotFoundException("Not authorised");
        }
    }

    public List<Meal> getFilteredByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return repository.filteredByDate(startDate, endDate, userId);
    }
}