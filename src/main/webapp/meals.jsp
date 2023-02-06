<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h3><a href="meals?action=insert">Add Meal</a></h3>
<hr>
<h2>Meals</h2>
<c:if test="${empty listOfMeals}">
    There are no meals to display
</c:if>
<c:if test="${not empty listOfMeals}">
<table bordercolor="black" border="1" cellpadding="7">
    <thead>
    <tr align="center">
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <c:forEach items="${listOfMeals}" var="meal">
        <tr style="color:${meal.excess ? 'red' : 'green' }">
            <td>${meal.dateTime.format(formatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&mealId=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>    
</table>
</c:if>
</body>
</html>