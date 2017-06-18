<%-- 
    Document   : forget_password
    Created on : 05-Nov-2015, 21:41:37
    Author     : Hashith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forget Password</title>
        <link rel="stylesheet" type="text/css" href="css/custom.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <link rel="stylesheet" type="text/css" href="css/my_style.css"> 
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
        <script type="text/javascript">
            $(function () {
                $("#footer").load("footer.jsp");
            });
        </script>
    </head>
    <body>
        <br>
        <div class="col-md-8 col-md-offset-4">
            <div class="col-lg-8">
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <p class="h4 text-center"> <span class="glyphicon glyphicon-lock"></span> Password Reset</p>
                    </div>
                    <div class="panel-body">
                        <form class="form-horizontal" role="form" method="post" action="resetPassword">
                            <div class="row">
                                <div class="col-lg-12">
                                    <p>Please Enter Your Registered e-mail </p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6">
                                    <input type="email" name="email" id="email" class="form-control" required="">
                                </div>
                                <div class="col-lg-6">
                                    <button type="submit" id="send" class="btn btn-primary">
                                        <span class="glyphicon glyphicon-send"></span> Send Reset Link
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="panel-footer">
                        <p class="text-danger">
                            If you don't want to reset your password <a href="index.jsp" class="glyphicon glyphicon-user">clickhere</a> to sign in
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <br><br><br><br>
    <br><br><br><br>
    <br><br><br><br>
    <br><br><br><br>
    <br><br><br><br>
    <div id="footer"></div>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
    <script type="text/javascript">
            $(document).ready(function () {
                var data = getUrlParameter('msg');
                if (data === '0') {
                    alert('Password Reset Link Was Send Successfuly.Please Check Your e-mail Inbox');
                    window.location.href = 'http://localhost:8080/diabAdviser/index.jsp';
                } else if (data === '1') {
                    alert('Something Went Wrong Please Check Your Internet Connection And Try Again Later');
                } else if (data === '2') {
                    alert('Email You Entered Is Not Registered e-mail');
                }
            });
            var getUrlParameter = function getUrlParameter(sParam) {
                var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                        sURLVariables = sPageURL.split('&'),
                        sParameterName,
                        i;

                for (i = 0; i < sURLVariables.length; i++) {
                    sParameterName = sURLVariables[i].split('=');

                    if (sParameterName[0] === sParam) {
                        return sParameterName[1] === undefined ? true : sParameterName[1];
                    }
                }
            };
            $("#send").click(function () {
                $("#send").attr('class', 'btn btn-primary disabled');
            });
            $("#email").keypress(function () {
                $("#send").attr('class', 'btn btn-primary');
            });
    </script>
</html>
