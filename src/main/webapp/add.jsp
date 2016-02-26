<%-- 
    Document   : add
    Created on : Feb 25, 2016, 5:40:36 PM
    Author     : Nicholas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Author</title>
    </head>
    <body>
        <form method="post" action="AuthorController?taskType=new">
            <label>Enter the name of the author</label>
            <input type="text" name="authorName"/>
            <input type="submit" value="save"/>
            <input type="button" value="cancel" onclick="location.href='AuthorController?taskType=cancel'"/>
        </form>
    </body>
</html>
