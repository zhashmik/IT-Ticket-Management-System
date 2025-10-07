<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register Employee</title>
</head>
<body>

<h2>Register New Employee</h2>

<c:if test="${not empty message}">
    <p style="color:green">${message}</p>
</c:if>

<form:form method="post" modelAttribute="employee" action="${pageContext.request.contextPath}/admin/register-employee">
    
    <label>Name:</label>
    <form:input path="name" required="true"/><br/><br/>
    
    <label>Email:</label>
    <form:input path="email" type="email" required="true"/><br/><br/>
    
    <label>Password:</label>
    <form:password path="password" required="true"/><br/><br/>
    
    <label>Department:</label>
    <form:input path="department"/><br/><br/>
    
    <label>Project:</label>
    <form:input path="project"/><br/><br/>
    
    <label>Manager ID:</label>
    <form:input path="managerId" type="number"/><br/><br/>
    
    <label>Roles:</label><br/>
    <c:forEach var="role" items="${roles}">
        <input type="checkbox" name="selectedRoles" value="${role.name}"/> ${role.name}<br/>
    </c:forEach>
    <br/>
    
    <button type="submit">Register Employee</button>
</form:form>

</body>
</html>