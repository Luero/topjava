package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.StorageInMemory;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meal");

        List<MealTo> mealToList = MealsUtil.filteredByStreams(StorageInMemory.transferMapToList(), LocalTime.MIN, LocalTime.MAX, StorageInMemory.CALORIES_PER_DAY);

        request.setAttribute("listOfMeals", mealToList);
        request.setAttribute("formatter", FORMATTER);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
