<%-- 
    Document   : add
    Created on : Feb 25, 2016, 5:40:36 PM
    Author     : Nicholas
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Book</title>
    </head>
    <body>
        <form method="post" action=<%= response.encodeURL("bookController?taskType=new")%>>
            <label>Enter the title of the book</label>
            <input type="text" name="title"/>
            <label>Enter the isbn</label>
            <input type="text" name="isbn"/>
            <label>Select the author</label>
            <select name="authorId">
                <c:forEach items="${dropDownAuthors}" var="items">
                    <option value="${items.authorId}">${items.authorName}</option>
                </c:forEach>
            </select>
            <input type="submit" value="save"/>
            <input type="button" value="cancel" onclick="location.href='bookController?taskType=cancel'"/>
        </form>
    </body>
</html>
