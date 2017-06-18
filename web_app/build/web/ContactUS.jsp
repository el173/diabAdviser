<%-- 
    Document   : ContactUS
    Created on : Oct 17, 2015, 7:08:53 PM
    Author     : Hashith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
        <script type="text/javascript">
            $(function () {
                $("#footer").load("footer.jsp");
            });
        </script>
        <title>Contact Us</title>
    </head>
    <body>
        <br><br>
        <div class="row"><div class="col-md-9 col-md-offset-5">
                <div class="col-lg-8 ">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <p class="h4 text-center"> <span class="glyphicon glyphicon-phone"></span> Contact Us</p>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <p>Name *</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6">
                                    <input type="text" id="name" class="form-control"/>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-lg-12">
                                    <p>E-Mail *</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6">
                                    <input type="email" id="mail" class="form-control"/>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-lg-12">
                                    <p>Subject *</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6">
                                    <input type="text" id="subject" class="form-control"/>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-lg-12">
                                    <p>Message *</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12">
                                    <textarea class="form-control" id="msg_body" rows="8"></textarea>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-7">
                                    <p><span class="glyphicon glyphicon-hand-right"></span> Note : * Fields are mandatory</p>
                                </div>
                                <div class="col-lg-offset-9">
                                    <p class="text-muted">(max 450 characters)</p>
                                </div>
                            </div>
                        </div>  
                        <div class="panel-footer">
                            <div class="row">
                                <div class=" col-lg-5">
                                    <p><a href="#" onclick="window.history.back();"><span class="glyphicon glyphicon-backward"></span> Click Here</a> to go back</p>
                                </div>
                                <div class="col-lg-offset-10">
                                    <a type="button" class="btn btn-info" id="contact" onclick="sendMessage()" ><span class="glyphicon glyphicon-send"></span> Send</a>
                                </div>
                            </div>
                        </div>
                    </div>  
                </div>  
            </div>    
        </div>
        <div id="footer"></div>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
        <script type="text/javascript">
                                        function sendMessage() {
                                            var name = $('#name').val().trim();
                                            var subject = $('#subject').val().trim();
                                            var mail = $('#mail').val().trim();
                                            var message = $('#msg_body').val().trim();

                                            if (name === '' || subject === '' || mail === '' || message === '') {
                                                alert('Please fill all fields');
                                            } else {
                                                if (validateForm()) {
                                                    alert('Message is sending please wait unit success message display');
                                                    $.post('contactUs', {
                                                        c_name: name,
                                                        c_sub: subject,
                                                        c_mail: mail,
                                                        c_msg: message
                                                    }, function (data, status) {
                                                        if (data === '1') {
                                                            alert('Message send, Administrator will contact you soon through email Thank You');
                                                            window.location.href = 'http://localhost:8080/diabAdviser/index.jsp';
                                                        } else {
                                                            alert('Something went wrong please try again !!');
                                                        }
                                                    });
                                                } else {
                                                    alert("Not a valid e-mail address");
                                                }
                                            }
                                        }
                                        function validateForm() {
                                            var x = $('#mail').val().trim();
                                            var atpos = x.indexOf("@");
                                            var dotpos = x.lastIndexOf(".");
                                            if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= x.length) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        }
        </script>
    </body>
</html>
