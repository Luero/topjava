package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.javawebinar.topjava.model.MealList;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meal");

        List<MealTo> mealToList = MealsUtil.filteredByStreams(MealList.mealList, LocalTime.MIN, LocalTime.MAX, MealList.CALORIES_PER_DAY);

        request.setAttribute("ListOfMeals", mealToList);
        request.setAttribute("Formatter", formatter);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
