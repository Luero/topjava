package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    private int authorisedUserId = SecurityUtil.authUserId();

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(authorisedUserId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> filteredByDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("filtered {}", startDate, endDate);
        return MealsUtil.getFilteredTos(service.getFilteredByDate(startDate, endDate, authorisedUserId),
                MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authorisedUserId);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authorisedUserId);
    }

    public MealTo save(Meal meal) {
        Collection<Meal> collection = new ArrayList<>();
        collection.add(service.create(meal, authorisedUserId));
        log.info("create {}", meal);
        return MealsUtil.getTos(collection, MealsUtil.DEFAULT_CALORIES_PER_DAY)
                .stream()
                .findAny()
                .get();
    }

    public void update(Meal meal, int id) {
        if(get(id) != null) {
            log.info("update {}", meal);
            service.update(meal, authorisedUserId);
        }
    }
}