<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<title><spring:message code="onemeal.title"/></title>
<section>
    <h2><spring:message code="${meal.isNew() ? 'mealform.create' : 'mealform.update'}"/></h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meal.datetime"/></dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/></dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/></dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="meal.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="meal.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>