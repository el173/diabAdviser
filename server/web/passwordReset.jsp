<%-- 
    Document   : passwordReset
    Created on : 07-Nov-2015, 12:18:17
    Author     : Hashith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/signup.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
        <title>Password Rest</title>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                if (getUrlParameter('msg') === '1') {
                    alert('Password Successfuly Changed');
                    window.location.href = 'http://localhost:8080/diabAdviser/index.jsp';
                } else if (getUrlParameter('msg') === '2') {
                    alert('Something went Password did not Changed');
                } else {
                    $.post('getPassword', {
                        val: getUrlParameter('value')
                    }, function (data, status) {
                        if (data === 'null') {
                            alert('Something went wrong please get the Password Reset Mail againg ');
                            window.location.href = 'http://localhost:8080/diabAdviser/forget_password.jsp';
                        } else {
                            $('#usr').val(data.split(',')[0]);
                            $('#crpass').val(data.split(',')[1]);
                        }
                    });
                }
                $('#crnwpass').keyup(function () {
                    var pass = $('#pass').val();
                    if (pass !== $(this).val()) {
                        $('#pass_not_match').removeClass('hidden');
                        $('#pass_match').addClass('hidden');
                        $('#btn_change').addClass('disabled');
                        $('#btn_change').attr('disabled', '');
                    } else {
                        $('#pass_not_match').addClass('hidden');
                        $('#pass_match').removeClass('hidden');
                        $('#btn_change').removeClass('disabled');
                        $('#btn_change').removeAttr('disabled', '');
                    }
                });
                $('#pass').keyup(function () {
                    if ($.trim($('#crnwpass').val()) !== '') {
                        var pass = $('#crnwpass').val();
                        if (pass !== $(this).val()) {
                            $('#pass_not_match').removeClass('hidden');
                            $('#pass_match').addClass('hidden');
                            $('#btn_change').addClass('disabled');
                            $('#btn_change').attr('disabled', '');
                        } else {
                            $('#pass_not_match').addClass('hidden');
                            $('#pass_match').removeClass('hidden');
                            $('#btn_change').removeClass('disabled');
                            $('#btn_change').removeAttr('disabled', '');
                        }
                    }
                });
            });
            $(function () {
                $("#footer").load("footer.jsp");
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
        </script>
    </head>
    <body>
        <br><br>
        <div class="col-md-7 col-md-offset-5">
            <div class="col-lg-8 ">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <p class="h4 text-center"> <span class="glyphicon glyphicon-lock"></span> Password Reset</p>
                    </div>
                    <div class="panel-body">
                        <form class="form-horizontal" role="form" id="frm" action="changePassword" method="post">
                            <div class="row">
                                <div class="col-md-12">
                                    <p><b>Current Password</b></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <input type="hidden" name="userid" id="usr">
                                    <input type="password" name="crpass" id="crpass" class="form-control" disabled="" required="">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-md-6"><p><b>New Password</b></p></div>
                                <div class="col-md-6"><p><b>Confirm New Password</b></p></div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <input type="password" name="pass" pattern=".{5,10}" title="5 to 10 characters" id="pass" class="form-control" required="">
                                </div>
                                <div class="col-md-6">
                                    <input type="password" name="crnwpass" pattern=".{5,10}" title="5 to 10 characters" id="crnwpass" class="form-control" required="">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-md-6">
                                    <p id="pass_not_match" class="text-danger hidden">
                                        <span class="glyphicon glyphicon-remove"></span> Password doesn't match
                                    </p>
                                    <p id="pass_match" class="text-success hidden">
                                        <span class="glyphicon glyphicon-ok"></span> Password match
                                    </p>
                                </div>
                                <div class="col-md-6 text-right">
                                    <button id="btn_change" type="submit" class="btn btn-success" disabled="">Change Password</button>
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
</html>
