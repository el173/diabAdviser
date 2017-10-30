<%-- 
    Document   : home
    Created on : Oct 17, 2015, 6:47:12 PM
    Author     : Hashith
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            if (session.getAttribute("user") == null) {
                response.sendRedirect("index.jsp?msg=end");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
        <script type="text/javascript">
            $(function () {
                $("#header").load("header.jsp");
            });
            $(function () {
                $("#footer").load("footer.jsp");
            });
        </script>
        <title>diabAdsiver</title>
    </head>
    <body>
        <div id="header"></div>
        <div id="footer"></div>
    </body>
</html>

