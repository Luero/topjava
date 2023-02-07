package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealDao;
import ru.javawebinar.topjava.repository.MealDaoImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private MealDao dao = new MealDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "" : action;

        switch (action) {
            case "update": {
                Meal meal = dao.get(Integer.parseInt(request.getParameter("mealId")));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("addAndCreateMeal.jsp").forward(request, response);
                log.debug("redirect to add and create page");
                break;
            }
            case "delete": {
                dao.delete(Integer.parseInt(request.getParameter("mealId")));
                response.sendRedirect("meals");
                log.debug("redirect to meals");
                break;
            }
            case "create": {
                request.getRequestDispatcher("addAndCreateMeal.jsp").forward(request, response);
                log.debug("redirect to add and create page");
            }
            default: {
                List<MealTo> mealToList = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, User.CALORIES_PER_DAY);
                request.setAttribute("listOfMeals", mealToList);
                request.setAttribute("formatter", FORMATTER);
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                log.debug("redirect to meals");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal;
        Integer id;
        try {
            id = Integer.parseInt(request.getParameter("mealId"));
        } catch (NumberFormatException e)
        {
            id = 0;
        }
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        if(id == 0)
        {
            meal = new Meal(id, dateTime, description, calories);
            dao.create(meal);
        } else
        {
            meal = new Meal(id, dateTime, description, calories);
            dao.update(meal);
        }

        response.sendRedirect("meals");
        log.debug("redirect to meals");
    }
}
