<%-- 
    Document   : index
    Created on : 19-Sep-2015, 19:35:57
    Author     : Hashith
--%>
<html>
    <head>
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="css/signup.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
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
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-7">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <span class="glyphicon glyphicon-lock"></span> Login</div>
                        <div class="panel-body">
                            <form class="form-horizontal" role="form" method="post" action="login">
                                <div class="form-group">
                                    <label for="inputEmail3" class="col-sm-3 control-label">
                                        Email</label>
                                    <div class="col-sm-9">
                                        <input type="email" name="email" class="form-control" id="inputEmail3" placeholder="Email" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputPassword3" class="col-sm-3 control-label">
                                        Password</label>
                                    <div class="col-sm-9">
                                        <input type="password" name="pass" class="form-control" id="inputPassword3" placeholder="Password" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-3 col-sm-9">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" name="rem" id="remember"/>
                                                Remember me
                                            </label><br><label class="text-warning small">(cookies must be enabled)</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group last">
                                    <div class="col-sm-offset-3 col-sm-9">
                                        <button type="submit" class="btn btn-success btn-sm">
                                            Sign in</button>
                                        <button type="reset" class="btn btn-default btn-sm">
                                            Reset</button>
                                    </div>
                                </div>
                                <div class="col-sm-offset-3 col-sm-9">
                                    <a href="forget_password.jsp">Forgot password?</a>
                                </div>
                            </form>
                        </div>
                        <div class="panel-footer">
                            Not Registred? <a href="signup.jsp">Register here</a></div>
                    </div>
                </div>
            </div>
        </div>
        <br><br><br>
        <div id="footer"></div>
    </body>
    <script type="text/javascript">
        /*   function login() {
         var un = document.getElementById('inputEmail3').value;
         var pw = document.getElementById('inputPassword3').value;
         var rm = document.getElementById('remember').checked;
         alert(rm);
         if (un !== '' && pw !== '') {
         $.post('login', {
         email: un,
         pass: pw,
         rem: rm
         }, function (data, status) {
         if (status === 'success') {
         if (data === '1') {
         alert('OK');
         window.location.href = "http://localhost:8080/diabAdviser/home.jsp";
         } else {
         alert('Please Check Login Details!!');
         }
         }
         });
         }
         }*/
        $(document).ready(function () {
            var un = getCookie('name');
            var pw = getCookie('password');
            var rm = getCookie('rem');
            if (rm === 'true') {
                $("#inputEmail3").val(un);
                $("#inputPassword3").val(pw);
                $('#remember').attr('checked', 'checked');
            }
            var error_parm = getUrlParameter('msg');
            if (error_parm === '1') {
                alert('Please Check Login Details!!');
            }
            if (error_parm === 'end') {
                alert('Session Has Being Expired or Ended Please Sign in Again');
            }
            if (error_parm === 'logout') {
                alert('See You Soon !!');
            }
            var value = getUrlParameter('value');
            if($.trim(value) !==''){
                $.post('accountActivator',{enc:value},function(data,status){
                    if(data === '0'){
                        alert('Your Account Has been Activated');
                        window.location.href = "http://localhost:8080/diabAdviser/index.jsp";
                    }
                });
            }
        });
        function getCookie(cname) {
            var name = cname + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) === ' ')
                    c = c.substring(1);
                if (c.indexOf(name) === 0)
                    return c.substring(name.length, c.length);
            }
            return "";
        }
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
    </script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
</html>

