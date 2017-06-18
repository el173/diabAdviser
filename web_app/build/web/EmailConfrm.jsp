<%-- 
    Document   : EmailConfrm
    Created on : 31-Dec-2014, 16:29:36
    Author     : Hashith
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/signup.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/custom.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css"/>        
        <link rel="stylesheet" type="text/css" href="css/mystyle.css">
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#btn_send').click(function () {
                    $('#btn_send').addClass('disabled');
                    sendEmail();
                    $('#btn_send').removeClass('disabled');
                });
                $('#btn_send1').click(function () {
                    $('#btn_send1').addClass('disabled');
                    sendEmail();
                    $('#btn_send1').removeClass('disabled');
                });
                $('#btn_tip').click(function () {
                    var tip_text = $.trim($('#tip').val());
                    if (tip_text === '') {
                        alert('Please do not skip this step');
                    } else {
                        $.post('saveTip', {
                            tip:tip_text
                        }, function (data, status) {
                            if(status !== 'success'){
                                $('#btn_tip').attr('disabled','');
                            }else{
                                if(data === '0'){
                                    $("#send").removeClass('hidden');
                                    $("#gettip").addClass('hidden');
                                }else{
                                    alert('Some thing went wrong please refresh the page');
                                }
                            }
                        });
                    }
                });
            });
            function sendEmail() {
                $.post('SendConformEmail', {}, function (data, status) {
                    if (data === '1') {
                        $("#sendErr").addClass('hidden');
                        $("#send").addClass('hidden');
                        $("#sendOK").removeClass('hidden');
                    } else if (data === '2') {
                        $("#sendErr").removeClass('hidden');
                        $("#send").addClass('hidden');
                        $('#btn_send').removeAttr('disables', '');
                        $('#btn_send1').removeAttr('disables', '');
                    } else {

                    }
                });
            }
        </script>
        <title>Confirm Your Details</title>
    </head>
    <body>
    <center>
        <br>
        <br>
        <br>
        <div class="container main hidden" id="send">
            <div>
                <br>
                <p class="h2">Welcome !</p><label>your almost done please activate your account with email confirm</label>
                <p class="h3">Click <a class="btn btn-success" id="btn_send">Here</a> to send your Confirm e-mail</p>
                <br>
            </div>
        </div>
        <div class="container" id="gettip">
            <br>
            <br>
            <div >
                <p class="h2" >One more thing before things are getting ready</p>
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <label class="text-muted h4">
                                As a help for this community please give us a general health tip for a Diabetic Patient. 
                            </label>
                        </div>
                    </div>
                    <hr class="divider h1">
                    <div class="row">
                        <div class="center-block">
                            <div class="col-md-5 col-md-offset-3">
                                <textarea class="form-control" id="tip" rows="5" maxlength="200"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="center-block">
                            <div class="col-md-5 col-md-offset-5">
                                <label class="h6 text-muted">(Max 200 letters)</label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-5 col-md-offset-3">
                            <button class="btn btn-block btn-primary" id="btn_tip"><span class="glyphicon glyphicon-forward"></span> Continue</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container main alert alert-warning hidden" id='sendErr'>
            <br>
            <br>
            <div >
                <p class="h3">Your confirm e-mail didn't send successfully please check your connection and Send it Again. </p>
                <p class="h3">Click <a class="btn btn-success" id="btn_send1">Here</a> to send your Confirm e-mail Again.</p>
            </div>
        </div>
        <div class="container main alert alert-success hidden" id="sendOK">
            <div>
                <br>
                <div>
                    <p class="h2"><span class="glyphicon glyphicon-ok"></span> Your confirm e-mail was successfully send</p>
                    <p class="h3">Please Check your inbox and confirm your account</p>
                    <h2><a href="index.jsp"><span class="glyphicon glyphicon-home"></span></a></h2>
                </div>
            </div>
        </div>
    </center>   
</body>
<br><br><br>
<br><br><br>
<br><br><br><br>
<%@include file="footer.jsp" %>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery-2.1.0.js"></script>
</html>
