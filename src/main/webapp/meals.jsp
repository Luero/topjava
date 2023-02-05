<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<%--@elvariable id="ListOfMeals" type="java.util.List"--%>
<c:if test="${empty ListOfMeals}">
    There are no meals to display
</c:if>
<c:if test="${not empty ListOfMeals}">
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
    <%--@elvariable id="ListOfMeals" type="java.util.List"--%>
    <c:forEach items="${ListOfMeals}" var="meal">
        <c:if test="${meal.excess == true}">
            <tr style="color: red">
        </c:if>
        <c:if test="${meal.excess == false}">
            <tr style="color: green">
        </c:if>
            <td><c:out value="${meal.dateTime.format(Formatter)}" /></td>
            <td><c:out value="${meal.description}" /></td>
            <td><c:out value="${meal.calories}" /></td>
        </tr>
    </c:forEach>    
</table>
</c:if>
</body>
</html>