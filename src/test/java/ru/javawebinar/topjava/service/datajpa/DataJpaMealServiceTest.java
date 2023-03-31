package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getWithUserByIdAndUserId() {
        Meal meal = service.getWithUserByIdAndUserId(meal1.id(), USER_ID);
        MEAL_MATCHER.assertMatch(meal, meal1);
        USER_MATCHER.assertMatch(meal.getUser(), UserTestData.user);
    }

    @Test
    public void getWithUserByIdAndUserIdNotFound() {
        assertThrows(NotFoundException.class, () -> service.getWithUserByIdAndUserId(MealTestData.NOT_FOUND, USER_ID));
    }

    @Test
    public void getWithUserByIdAndUserIdNotOwn() {
        assertThrows(NotFoundException.class, () -> service.getWithUserByIdAndUserId(meal1.id(), ADMIN_ID));
    }
}
