<%-- 
    Document   : authors
    Created on : Feb 8, 2016, 12:18:27 PM
    Author     : Nicholas
--%>

<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="style.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
    <center><input type="submit" name="" value="Add Book" class="btn-danger" id="authorId" onclick="location.href='bookController?taskType=add'"/></center>
    <table style="background-color: ${table}">
        <thead>
        <tr>
            <th>
                Book ID
            </th>
            <th>
                Book Title
            </th>
            <th>Book Isbn</th>
            <th>Book Author</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${books}" var="item">
                <tr>
                    <td> ${item.bookId} </td>
                    <td>
                        ${item.title}
                    </td>
                    <td>
                        ${item.isbn}
                    </td>
                    <td>${item.authorId.authorName}</td>
                    <td>
                        <input name="edit" value="Edit" type="button" class="btn-primary" id="Id" onclick="location.href='bookController?taskType=edit&id=${item.bookId}'"/>
                    </td>
                    <td>
                        <input type="submit" name="" value="Delete" class="btn-danger" id="authorId" onclick="location.href='bookController?taskType=deleteBook&id=${item.bookId}'"/>
                    </td>
                    </tr>
            </c:forEach>
        </tbody>
    </table>
    <style>
        td,th{
            color: ${text};
        }
    </style>
        <a href="index.jsp"><button type="button" id="goBack" class="btn btn-primary">Go Back</button></a>
        <script src="//code.jquery.com/jquery-1.12.0.min.js"/>
    </body>
</html>
