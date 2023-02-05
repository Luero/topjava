<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%=request.getAttribute("ListOfMeals")%>
<%=request.getAttribute("Formatter")%>
<html lang="ru">
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal</h2>

<table border="2" cellpadding="7">
    <caption>List of Meals</caption>
    <tr align="center">
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <%--@elvariable id="ListOfMeals" type="java.util.List"--%>
    <c:forEach items="${ListOfMeals}" var="meal">
        <tr>
            <td>
            <c:out value="${Formatter.parse(meal.dateTime)}" /></td>
            <td><c:out value="${meal.description}" /></td>
            <td><c:out value="${meal.calories}" /></td>
            <td><c:out value="${meal.excess}" /></td>
        </tr>
    </c:forEach>    
</table>
</body>
</html>