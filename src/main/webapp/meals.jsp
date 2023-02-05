<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal</h2>

<table border="2" cellpadding="7">
    <caption> ${MealsList} </caption>
    <tr>
        <th>Date align="center"</th>
        <th>Description align="center"</th>
        <th>Calories align="center"</th>
        <th></th>
        <th></th>
    </tr>
</table>

</body>
</html>