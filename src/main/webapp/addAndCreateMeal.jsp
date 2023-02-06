<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<form method="POST" action='meals'>
    <input type="hidden" readonly="readonly" name="mealId"
           value="${meal.id}"/> <br/>
    DateTime : <input
        type="datetime-local" name="dateTime"
        value="${meal.dateTime}"/> <br/>
    Description : <input
        type="text" name="description"
        value="${meal.description}"/> <br/>
    Calories : <input type="number" name="calories"
                      value="${meal.calories}"/> <br/>
    <p><input type="submit" value="Submit"/>
        <a href="meals"><input type="button" value="Cancel"></a></p>
</form>
</body>
</html>