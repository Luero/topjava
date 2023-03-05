package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(meal1.getId(), UserTestData.USER_ID);
        MealTestData.assertMatch(meal, meal1);
    }

    @Test
    public void getByIncorrectUserId() {
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId(), UserTestData.ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    public void delete() {
        service.delete(meal1.getId(), UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId(), UserTestData.USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    public void deleteByIncorrectUserId() {
        assertThrows(NotFoundException.class, () -> service.delete(meal2.getId(), UserTestData.ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> mealsBetweenInclusive = service.getBetweenInclusive(meal7.getDate(), meal10.getDate(), UserTestData.ADMIN_ID);
        assertMatch(mealsBetweenInclusive, Arrays.asList(meal7, meal8, meal9, meal10));
    }

    @Test
    public void getAll() {
        List<Meal> mealList = service.getAll(UserTestData.USER_ID);
        assertMatch(mealList, Arrays.asList(meal6, meal5, meal4, meal3, meal2, meal1));
    }

    @Test
    public void update() {
        Meal updated = new Meal(meal11.getId(), meal11.getDateTime(), meal11.getDescription(), meal11.getCalories());
        updated.setDescription("Updated meal description");
        service.update(updated, UserTestData.ADMIN_ID);
        assertMatch(service.get(meal11.getId(), UserTestData.ADMIN_ID), updated);
    }

    @Test
    public void updateByIncorrectUserId() {
        Meal updated = new Meal(meal11.getId(), meal11.getDateTime(), meal11.getDescription(), meal11.getCalories());
        updated.setDescription("Updated meal description");
        assertThrows(NotFoundException.class, () -> service.update(updated, UserTestData.USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), UserTestData.USER_ID);
        Integer mealId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(mealId);
        assertMatch(created, newMeal);
        assertMatch(service.get(created.getId(), UserTestData.USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, LocalDateTime.of(2023, Month.FEBRUARY, 16, 17, 00),
                        "Duplicate", 300), UserTestData.USER_ID));
    }
}