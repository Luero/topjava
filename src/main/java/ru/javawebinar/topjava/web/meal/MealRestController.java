package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> filteredByDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("filtered {} {}", startDate, endDate);
        return MealsUtil.getFilteredTos(service.getFilteredByDate(startDate == null ? LocalDate.MIN : startDate,
                        endDate == null ? LocalDate.MAX : endDate, SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay(),
                startTime == null ? LocalTime.MIN : startTime, endTime == null ? LocalTime.MAX : endTime);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        ValidationUtil.checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }
}