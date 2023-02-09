<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date"/>

<html lang="ru">
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${meal.id eq null ? "Add meal" : "Edit meal"}</h2>
<form method="POST" action='meals'>
    <input type = "hidden" readonly="readonly" name="mealId"
           value="${meal.id}"/> <br/>
    DateTime : <input
        type="datetime-local" name="dateTime"
        <c:if test="${meal.id eq null}">
            value="<fmt:formatDate type="time" value="${now}" pattern="yyyy-MM-dd HH:mm"/>"
        </c:if>
            value="${meal.dateTime}" /> <br/>
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