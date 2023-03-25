package ru.javawebinar.topjava.service.service.tests;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTestBase;

@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = Profiles.JPA)
public class UserServiceJpaTest extends UserServiceTestBase {
}
