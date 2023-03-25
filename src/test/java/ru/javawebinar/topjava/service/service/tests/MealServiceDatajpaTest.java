package ru.javawebinar.topjava.service.service.tests;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.MealServiceTestBase;

@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = "datajpa")
public class MealServiceDatajpaTest extends MealServiceTestBase {
}
