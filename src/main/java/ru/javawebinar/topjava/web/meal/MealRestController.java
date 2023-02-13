package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.service.MealService;

import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    private int authorisedUserId = SecurityUtil.authUserId();

    public List<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(authorisedUserId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> filteredByDate(LocalDate startDate, LocalDate endDate) {
        return MealsUtil.getTos(service.getFilteredByDate(startDate, endDate, authorisedUserId),
                MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> filteredByTime(LocalTime startTime, LocalTime endTime) {
        // подумать, нужно ли брать список, уже отсортированный по дате, или всю еду
    }

}