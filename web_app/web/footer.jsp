<%-- 
    Document   : footer
    Created on : Oct 17, 2015, 12:54:05 PM
    Author     : Hashith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/custom.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="icon" type="image/vnd.microsoft.icon" href="image/favicon.ico">
        <!--<script type="text/javascript" src="js/jquery-2.1.0.js"></script>-->
        <title>Footer</title>
    </head>
    <body>
        <br><br>
        <div class="panel panel-footer panel-default">
            <div class="panel-footer" style="background-color: #ffffff;">
                <div class="row">
                    <div class="col-lg-4 col-xs-5" style="text-align: center;">
                        <div class="caption">
                            <h4><span class="glyphicon glyphicon-tag"></span> Information</h4>
                            <ul class="list-unstyled">
                                <li><a class="btn btn-sm" data-toggle="modal" data-target="#policy">User Policy</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-4 col-xs-5" style="text-align: center;">
                        <div class="caption">
                            <h4><span class="glyphicon glyphicon-tag"></span> Site</h4>
                            <ul class="list-unstyled">
                                <li><a class="btn btn-sm" data-toggle="modal" data-target="#company">Company</a></li>
                                <li><a class="btn btn-sm" href="ContactUS.jsp">Contact Us</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-4 col-xs-5" style="text-align: center;">
                        <div class="caption">
                            <br>
                            <address>
                                <strong>diabAdviser</strong><br>
                                Online Diabetes Advising Community 
                            </address>
                        </div>
                    </div>
                </div>
                <div class="pull-right">
                    <img src="image/paypal-verified.png" width="200px"/> Copyright © 2015
                    <a class="label label-default" href="https://www.duractteam.com">
                        DURACT</a> All Rights Reserved.
                </div>
                <br>
            </div>
            <div class="modal fade" id="policy" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">User Policy</h4>
                        </div>
                        <div class="modal-body">
                            <p>
                                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                                cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
                                proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                            </p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="company" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">Company</h4>
                        </div>
                        <div class="modal-body">
                            <p>
                            <address>
                                <strong>diabAdviser</strong><br>
                                Kuliyapitiya,<br>
                                Sri Lanka.<br>
                                <abbr title="Call Us">P:</abbr> 
                            </address>
                            </p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <!--<script type="text/javascript" src="js/bootstrap.min.js"></script>-->
<!--    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.0.js"></script>-->
</html>

