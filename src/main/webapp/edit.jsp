<%-- 
    Document   : edit
    Created on : Feb 24, 2016, 9:01:27 PM
    Author     : Nicholas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Author</title>
    </head>
    <body>
        <form method="post" action="AuthorController?taskType=save">
        <input type="number" readonly name=authorId value="${author.authorId}" />
            <input type="text" name="authorName" value="${author.authorName}"/>
            <input type="date" readonly name="dateadded" value="${author.dateAdded}" />
            <input type="submit" value="save"/>
            <input type="submit" value="cancel"/>
        </form>
    </body>
</html>
