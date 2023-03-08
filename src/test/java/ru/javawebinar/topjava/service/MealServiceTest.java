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
        Meal meal = service.get(userMeal1.getId(), UserTestData.USER_ID);
        MealTestData.assertMatch(meal, userMeal1);
    }

    @Test
    public void getByIncorrectUserId() {
        assertThrows(NotFoundException.class, () -> service.get(userMeal1.getId(), UserTestData.ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    public void delete() {
        service.delete(userMeal1.getId(), UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(userMeal1.getId(), UserTestData.USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    public void deleteByIncorrectUserId() {
        assertThrows(NotFoundException.class, () -> service.delete(userMeal2.getId(), UserTestData.ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> mealsBetweenInclusive = service.getBetweenInclusive(adminMeal1.getDate(), adminMeal4.getDate(), UserTestData.ADMIN_ID);
        assertMatch(mealsBetweenInclusive, Arrays.asList(adminMeal4, adminMeal3, adminMeal2, adminMeal1));
    }

    @Test
    public void getBetweenInclusiveWithNulls() {
        List<Meal> mealsBetweenInclusive = service.getBetweenInclusive(null, null, UserTestData.ADMIN_ID);
        assertMatch(mealsBetweenInclusive, Arrays.asList(adminMeal6, adminMeal5, adminMeal4, adminMeal3, adminMeal2, adminMeal1));
    }

    @Test
    public void getAll() {
        List<Meal> mealList = service.getAll(UserTestData.USER_ID);
        assertMatch(mealList, Arrays.asList(userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, UserTestData.ADMIN_ID);
        assertMatch(service.get(adminMeal5.getId(), UserTestData.ADMIN_ID), getUpdated());
    }

    @Test
    public void updateByIncorrectUserId() {
        Meal updated = new Meal(adminMeal5.getId(), adminMeal5.getDateTime(), adminMeal5.getDescription(), adminMeal5.getCalories());
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
                service.create(new Meal(null, userMeal1.getDateTime(), "Duplicate", 300), UserTestData.USER_ID));
    }
}