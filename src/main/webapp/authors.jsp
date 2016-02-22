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
    <table>
        <thead>
        <tr>
            <th>
                Author Name
            </th>
            <th>
                Author Number
            </th>
            <th>
                Author Add Date
            </th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${authors}" var="item">
                <tr>
                    <td> ${item.authorName} </td>
                    <td> ${item.authorId} </td>
                    <td>
                        <fmt:formatDate pattern="MM-dd-yyyy" 
                            value="${item.dateAdded}" />
                    </td>
                    </tr>
            </c:forEach>
        </tbody>
    </tabel>
    <div class="container" id="goBackContainer">
        <a href="index.html"><button type="button" id="goBack" class="btn btn-primary">Go Back</button></a>
        </div>
</html>
