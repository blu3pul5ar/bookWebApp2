<%-- 
    Document   : edit
    Created on : Feb 24, 2016, 9:01:27 PM
    Author     : Nicholas
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Book</title>
    </head>
    <body>
        <form method="post" action=<%= response.encodeURL("bookController?taskType=save")%>>
            <label>Book Id</label>
            <input type="text" value="${book.bookId}" readonly/>
            <label>Enter the title of the book</label>
            <input type="text" name="title" value="${book.title}"/>
            <label>Enter the isbn</label>
            <input type="text" name="isbn" value="${book.isbn}"/>
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
