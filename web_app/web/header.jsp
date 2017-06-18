<%-- 
    Document   : header
    Created on : Oct 17, 2015, 6:58:50 PM
    Author     : Hashith
--%>
<%@taglib  prefix="pat" uri="getDetail"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">
        <link href="css/sb-admin-2.css" rel="stylesheet">
        <link href="font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">
        <link href="css/plugins/dataTables.bootstrap.css" rel="stylesheet">
        <link href="css/sb-admin-2.css" rel="stylesheet">
        <link href="font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <script type="text/javascript">
            $(document).ready(function () {
                $('#patient_details').DataTable();
                $('#payment_details').DataTable();
                $('#history_details').DataTable();
                $('#sugar_detail').DataTable();
                $('#wrapper').focus();
                $('html,body').animate({scrollTop: $('#wrapper').offset().top}, 1000);
                $('#dash').click(function () {
                    hideAll();
                    $('#board').removeClass('hidden');
                    $('#dash').addClass('active');
                });
                $('#patient').click(function () {
                    hideAll();
                    $('#pat').removeClass('hidden');
                    $('#patient').addClass('active');
                });
                $('#payment').click(function () {
                    hideAll();
                    $('#pay').removeClass('hidden');
                    $('#payment').addClass('active');
                });
                $('#message').click(function () {
                    hideAll();
                    $('#msg').removeClass('hidden');
                    $('#message').addClass('active');
                });
                $('#waiting').click(function () {
                    hideAll();
                    $('#msg').removeClass('hidden');
                    $('#message').addClass('active');
                });
                $('#history').click(function () {
                    hideAll();
                    $('#pat_his').removeClass('hidden');
                    $('#history').addClass('active');
                });
                $('#patient2').click(function () {
                    hideAll();
                    $('#pat').removeClass('hidden');
                    $('#patient').addClass('active');
                });
                $('#payment2').click(function () {
                    hideAll();
                    $('#pay').removeClass('hidden');
                    $('#payment').addClass('active');
                });
                $('#message2').click(function () {
                    hideAll();
                    $('#msg').removeClass('hidden');
                    $('#message').addClass('active');
                });
                $.post('getUerName', {}, function (data, status) {
                    $('#user_name').html(data);
                });
                $.post('getPatientCount', {}, function (data, status) {
                    $('#pat_count').html(data);
                });
                $.post('getUnreadMessgeCount', {}, function (data, status) {
                    $('#new_msg1').html(data);
                    $('#new_msg').html(data);
                });
                $.post('getReceivedPayment', {}, function (data, status) {
                    $('#payment_received').html(data);
                });
                $.post('getNotRepliedMessageCount', {}, function (data, status) {
                    $('#msg_wait').html(data);
                });
                $('#contact_admin').click(function () {
                    $('#admin_msg').removeClass('hidden');
                    $('#admin_msg').focus();
                    $('html,body').animate({scrollTop: $('#admin_msg').offset().top}, 1000);
                });
                $('#admin_close').click(function () {
                    $('#admin_msg').addClass('hidden');
                    $('html,body').animate({scrollTop: $('#wrapper').offset().top}, 1000);
                });
                $('#admin_send').click(function () {
                    var sub = $('#admin_sub').val().trim();
                    var msg = $('#admin_body').val().trim();
                    if (sub === '' || msg === '') {
                        alert('Enter the subject and message');
                    } else {
                        $('#admin_send').addClass('disabled');
                        $.post('contactSuperUser', {
                            msg_subject: sub,
                            msg_content: msg
                        }, function (data, status) {
                            if (data === '1') {
                                alert('Message send, Administrator will contact you soon through email Thank You');
                                $('#admin_msg').addClass('hidden');
                                $('html,body').animate({scrollTop: $('#wrapper').offset().top}, 1000);
                                $('#admin_sub').val('');
                                $('#admin_body').val('');
                            } else {
                                alert('Something went wrong !!!');
                            }
                            $('#admin_send').removeClass('disabled');
                        });
                    }
                });
            });
            function viewMessage(messageId) {
                $.post('getMessageDetails', {msgId: messageId}, function (data, status) {
                    $('#show_msg').html(data);
                    $('#msg_read_area').focus();
                    $('html,body').animate({scrollTop: $('#msg_read_area').offset().top}, 1000);
                });
            }
            function reply() {
                var msgId = $('#msg_id').val();
                var rep = $('#msg_reply').val().trim();
                if (rep === '') {
                    alert('Please Enter Health Advise');
                } else {
                    if (rep.length > 450) {
                        alert('Please Check the Character Count');
                    } else {
                        $.post('sendReplyHealthTip', {msg_id: msgId,
                            reply: rep}, function (data, status) {
                            if (data === '0') {
                                alert('Health Advise Send');
                                location.reload();
                            }
                        });
                    }
                }
            }
            function hideAll() {
                $('#board').addClass('hidden');
                $('#pat').addClass('hidden');
                $('#pay').addClass('hidden');
                $('#msg').addClass('hidden');
                $('#pat_his').addClass('hidden');
                $('#msg_read_area').addClass('hidden');
                $('#admin_msg').addClass('hidden');
                $('#dash').removeClass('active');
                $('#patient').removeClass('active');
                $('#payment').removeClass('active');
                $('#message').removeClass('active');
                $('#history').removeClass('active');
            }
        </script>
        <title>header</title>
    </head>
    <body>
        <div id="wrapper">
            <nav class="navbar navbar-default navbar-static-top">
                <div class="container-fluid">

                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="home.jsp">diabAdviser</a>
                    </div>

                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <!--                        <ul class="nav navbar-nav">
                                                    <li><a href="#">Menu <span class="sr-only">(current)</span></a></li>
                                                    <li><a href="#">Menu</a></li>
                                                </ul>-->
                        <ul class="nav navbar-nav navbar-right">
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span> <label id="user_name">User Name</label> <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="getProfile">User Settings</a></li>
                                    <li><a href="#" id="contact_admin">Contact Administrator</a></li>
                                    <li role="separator" class="divider"></li>
                                    <li><a href="/diabAdviser/logout"><span class="glyphicon glyphicon-log-out"></span> Sign Out</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="navbar-default sidebar panel panel-default" role="navigation">
                    <div class="sidebar-nav navbar-default">
                        <ul class="nav" id="side-menu">
                            <li style="text-align: center">
                                <span class="glyphicon glyphicon-plus-sign fa-5x" style="color: #d9534f"></span>
                            </li>
                            <li>
                                <a class="active" href="#" id="dash"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa fa-users fa-fw"></i> Patients
                                    <span class="fa arrow"></span>
                                </a>
                                <ul class="nav nav-second-level">
                                    <li>
                                        <a href="#" id="patient"><span class="glyphicon glyphicon-user"></span> All Patients</a>
                                    </li>
                                    <li>
                                        <a href="#" id="history"><span class="glyphicon glyphicon-list"></span> Patient History</a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                            <li>
                                <a  href="#" id="payment"><i class="fa fa-briefcase fa-fw"></i> View Payments</a>
                            </li>
                            </li>
                            <li>
                                <a href="#" id="message"><i class="fa fa-envelope fa-fw"></i> Messages <span class="badge" id="new_msg">0</span></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
        <div id="board">
            <div id="page-wrapper" class="panel panel-default">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Dashboard</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-4 col-md-7">
                        <div class="panel panel-red">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-users fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge" id="pat_count">0</div><br>
                                        <div>Patient(s)</div>
                                    </div>
                                </div>
                            </div>
                            <a href="#" id="patient2">
                                <div class="panel-footer">
                                    <span class="pull-left">View All Patients</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-7">
                        <div class="panel panel-yellow">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-envelope-o fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge" id="new_msg1">0</div><br>
                                        <div>New Messages</div>
                                    </div>
                                </div>
                            </div>
                            <a href="#" id="message2">
                                <div class="panel-footer">
                                    <span class="pull-left">View Messages</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-7">
                        <div class="panel panel-green">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-briefcase fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge" id="payment_received">0.00</div>
                                        <div>LKR</div>
                                        <div>Received Payments</div>
                                    </div>
                                </div>
                            </div>
                            <a href="#" id="payment2">
                                <div class="panel-footer">
                                    <span class="pull-left">View All Payments</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-7">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-warning fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge" id="msg_wait">0</div>
                                        <div>Waiting For Reply </div>
                                    </div>
                                </div>
                            </div>
                            <a href="#" id="waiting">
                                <div class="panel-footer">
                                    <span class="pull-left">View Messages</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="pat" class="hidden">
            <div id="page-wrapper" class="panel panel-default">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Patients</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Patient Details
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover" id="patient_details">
                                        <thead>
                                            <tr>
                                                <th>Patient Name</th>
                                                <th>Mobile</th>
                                                <th>E-Mail Address</th>
                                                <th>Since</th>
                                                <th>Connection Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <pat:getPatients/>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="pay" class="hidden">
            <div id="page-wrapper" class="panel panel-default">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Payment Details</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Payment Details
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover" id="payment_details">
                                        <thead>
                                            <tr>
                                                <th>Patient Name</th>
                                                <th>Paid Date</th>
                                                <th>Amount (LKR)</th>
                                                <th>Maintain Charge</th>
                                                <th data-toggle="tooltip" data-placement="top" title="(amount-maintain charge)">Net Amount (LKR)</th>
                                                <th>Claimed</th>
                                                <th>Claimed Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <pat:getPayments/>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="msg" class="hidden">
            <div id="page-wrapper" class="panel panel-default">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Messages</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Patient Sugar Rates
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover" id="sugar_detail">
                                        <thead>
                                            <tr>
                                                <th>Patient Name</th>
                                                <th>Sugar Rate (mg/dL)</th>
                                                <th>Messages</th>
                                                <th>Date</th>
                                                <th>Action</th>
                                                <th>Replied</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <pat:getMessages/>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="pat_his" class="hidden">
            <div id="page-wrapper" class="panel panel-default">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Patients History</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Activity History
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover" id="history_details">
                                        <thead>
                                            <tr>
                                                <th>Patient Name</th>
                                                <th>Sugar Rate (mg/dL)</th>
                                                <th>Received Date</th>
                                                <th>Health Advice</th>
                                                <th>Replied Date</th>
                                                <th>Payment</th>
                                                <th>Seen</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <pat:getHistory/>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="admin_msg" class="hidden">
            <br>
            <div id="page-wrapper" class="panel panel-default">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Contact Administrator</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <p class="h4">Subject</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6">
                        <input type="text" class="form-control msg-content" id="admin_sub"/>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-lg-12">
                        <p class="h4">Message</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <textarea class="form-control msg-content" rows="8" id="admin_body"></textarea>
                    </div>
                </div>
                <br><br>
                <div class="row">
                    <div class="col-lg-6">
                        <button class="btn btn-danger" id="admin_close"><span class="glyphicon glyphicon-remove-circle"></span> Close</button>
                    </div>
                    <div class="col-lg-offset-10">
                        <button class="btn btn-success" id="admin_send"><span class="glyphicon glyphicon-send"></span> Send Message</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="show_msg"></div>
    </body>    
    <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/plugins/metisMenu/metisMenu.min.js"></script>
    <script src="js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="js/plugins/dataTables/dataTables.bootstrap.js"></script>
    <script src="js/sb-admin-2.js"></script>
</html>
