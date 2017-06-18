<%-- 
    Document   : admin_home
    Created on : 24-Oct-2015, 08:50:49
    Author     : Hashith
--%>
<%@taglib prefix="a" uri="adminDetails"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            if (session.getAttribute("admin") == null) {
                response.sendRedirect("login.jsp?msg=end");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="../js/jquery-2.1.0.js"></script>
        <script type="text/javascript" src="../js/bootstrap.js"></script>
        <script src="../js/plugins/dataTables/jquery.dataTables.js"></script>
        <script src="../js/plugins/dataTables/dataTables.bootstrap.js"></script>
        <script src="js/plugins/metisMenu/metisMenu.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/login.css">
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
        <link href="../css/plugins/dataTables.bootstrap.css" rel="stylesheet">
        <link href="../font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <script type="text/javascript">
            function dataToTables() {
                $('#payment_details').dataTable();
                $('#doctor_list').dataTable();
                $('#patient_list').dataTable();
            }
            function claim(payId) {
                $.post('../claimPayment', {id: payId}, function (data, status) {
                    if (data === '1') {
                        alert('Payment Done');
                        location.reload();
                    } else {
                        alert('Something went wrong');
                    }
                });
            }
            function activateAcc(id) {
                if (confirm('Are you sure do you want to continue')) {
                    $.post('../accountHandler', {
                        userId: id,
                        action: 1
                    }, function (data, status) {
                        if (data === '1') {
                            alert('Account has been activated');
                            location.reload();
                        }
                    });
                }
            }
            function deactivateAcc(id) {
                if (confirm('Are you sure do you want to continue')) {
                    $.post('../accountHandler', {
                        userId: id,
                        action: 2
                    }, function (data, status) {
                        if (data === '2') {
                            alert('Account has been deactivated');
                            location.reload();
                        }
                    });
                }
            }
            function showPayment() {
                $('html,body').animate({scrollTop: $('#payment').offset().top}, 1000);
            }
            function showDoctor() {
                $('html,body').animate({scrollTop: $('#doctor').offset().top}, 1000);
            }
            function showPatient() {
                $('html,body').animate({scrollTop: $('#patient').offset().top}, 1000);
            }
        </script>
        <title>diabAdviser Admin</title>
    </head>
    <body onload="dataToTables()">
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="admin_home.jsp">diabAdviser Admin Panel  v1.0</a>
                </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#" onclick="showPayment()"><span class="glyphicon glyphicon-briefcase"></span> Payment Details</a></li>
                        <li><a href="#" onclick="showDoctor()"><span class="glyphicon glyphicon-user"></span> Doctor Details</a></li>
                        <li><a href="#" onclick="showPatient()"><span class="glyphicon glyphicon-list"></span> Patient Details</a></li>
                        <li><a href="https://mail.google.com" target="_blank"><span class="glyphicon glyphicon-envelope"></span> Go to Mail</a></li>
                        <li><a href="../adminlogout"><span class="glyphicon glyphicon-log-out"></span> Log Out</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <br><br>
        <br>
        <div class="container">
            <div class="row" id="payment">
                <div class="panel panel-default">
                    <div class="page-header">
                        <p class="center-block h3">&nbsp;<span class="glyphicon glyphicon-briefcase"></span>  Payment Details</p>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="payment_details">
                                <thead>
                                    <tr>
                                        <th>Patient Name</th>
                                        <th>Doctor Name</th>
                                        <th>Paid Amount(LKR)</th>
                                        <th>Paid Date</th>
                                        <th>Admin Charge</th>
                                        <th>Net Amount(LKR)</th>
                                        <th>Claimed</th>
                                        <th>Claimed Date</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <a:getPayment/>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row" id="doctor">
                <div class="panel panel-default">
                    <div class="page-header">
                        <p class="center-block h3">&nbsp;<span class="glyphicon glyphicon-user"></span>  Doctors List</p>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="doctor_list">
                                <thead>
                                    <tr>
                                        <th>Doctor Name</th>
                                        <th>E-mail</th>
                                        <th>Mobile</th>
                                        <th>Bank Details</th>
                                        <th>Reg Date</th>
                                        <th>Status</th>
                                        <th>Amount to Claim (LKR)</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <a:getDoctor/>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="row" id="patient">
                <div class="panel panel-default">
                    <div class="page-header">
                        <p class="center-block h3">&nbsp;<span class="glyphicon glyphicon-list-alt"></span>  Patient List</p>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="patient_list">
                                <thead>
                                    <tr>
                                        <th>Patient Name</th>
                                        <th>E-mail</th>
                                        <th>Mobile</th>
                                        <th>Doctor</th>
                                        <th>Reg Date</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <a:getPatient/>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript" src="../js/jquery-2.1.0.js"></script>
        <script type="text/javascript" src="../js/bootstrap.js"></script>
        <script src="../js/plugins/dataTables/jquery.dataTables.js"></script>
        <script src="../js/plugins/dataTables/dataTables.bootstrap.js"></script>
        <script src="js/plugins/metisMenu/metisMenu.min.js"></script>
    </body>
</html>
