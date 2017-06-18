<%-- 
    Document   : signup
    Created on : Oct 17, 2015, 1:33:25 PM
    Author     : Hashith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/signup.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <script type="text/javascript" src="js/signup.js"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/npm.js"></script>
        <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                getHospitals();
                getBanks();
                $('#reg').addClass('disabled');
                $('#reg').attr('disabled', '');
                $('#agree_box').prop('checked', false);
                $.post('getMaintainCost', {}, function (data, status) {
                    $('#maintain').html(data);
                });
                $("#add_hospital").click(function () {
                    var name = $('#new_hospital').val();
                    if ($.trim(name) !== '') {
                        $.post('addNewHospital', {
                            hospital_name: $.trim(name)
                        }, function (data, status) {
                            if (data === '0') {
                                alert('Details Saved');
                                getHospitals();
                                $('#newHospital').modal('hide');
                            } else {
                                alert('Something went wrong or hospital already inserted please try again');
                            }
                        });
                    } else {
                        alert('Please Enter a Hospital Name');
                    }
                });
                $('#add_bank').click(function () {
                    var name = $('#new_bank').val();
                    if ($.trim(name) !== '') {
                        $.post('addNewBank', {
                            bank_name: name
                        }, function (data, status) {
                            if (data === '0') {
                                alert('Details Saved');
                                getBanks();
                            } else {
                                alert('Something went wrong or bank already inserted please try again');
                            }
                        });
                    } else {
                        alert('Please Enter a Bank Name');
                    }
                });
                $('#password_confirmation').keyup(function () {
                    var pass = $('#password').val();
                    if (pass !== $(this).val()) {
                        $('#pass_not_match').removeClass('hidden');
                        $('#pass_match').addClass('hidden');
//                        $('#reg').addClass('disabled');
//                        $('#reg').attr('disabled', '');
                    } else {
                        $('#pass_not_match').addClass('hidden');
                        $('#pass_match').removeClass('hidden');
//                        $('#reg').removeClass('disabled');
//                        $('#reg').removeAttr('disabled', '');
                    }
                });
                $('#t_and_c').change(function () {
                    if ($(this).is(':checked')) {
                        $('#reg').removeClass('disabled');
                        $('#reg').removeAttr('disabled', '');
                    } else {
                        $('#reg').addClass('disabled');
                        $('#reg').attr('disabled', '');
                    }
                });
                var error_parm = getUrlParameter('msg');
                if(error_parm === 'hospital'){
                    alert('Please Select a Hospital');
                }else if(error_parm === 'bank'){
                    alert('Please Select a Bank');
                }else if(error_parm === 'pass'){
                    alert('Password does not match');
                }else if(error_parm === 'email'){
                    alert('E-mail Address is Already Exist');
                }else if(error_parm === 'log'){
                    alert('Something went wrong Registation Faild Please try againg later');
                }
               
            });
            $(function () {
                $("#footer").load("footer.jsp");
            });
            function getHospitals() {
                $.post('getHospitalList', {}, function (data, status) {
                    var result = $.parseJSON(data);
                    $('#hospital').empty();
                    $('#hospital').append($('<option></option>').val('0').html('Select Hospital'));
                    $.each(result, function (id, value) {
//                        alert(id + ' ' + value);
                        $('#hospital').append($('<option></option>').val(id+1).html(value));
                    });
                });
            }
            function getBanks() {
                $.post('getBankList', {}, function (data, status) {
                    var result = $.parseJSON(data);
                    $('#bank_name').empty();
                    $('#bank_name').append($('<option></option>').val('0').html('Selecet Bank'));
                    $.each(result, function (id, value) {
//                        alert(id + ' ' + value);
                        $('#bank_name').append($('<option></option>').val(id+1).html(value));
                    });
                });
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
        <title>Sign Up</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
                    <form role="form" id="sign_up" action="signup" method="POST">
                        <h2>Please Sign Up <small>To continue....</small></h2>
                        <hr class="colorgraph">
                        <h4 style="color: #428bca">Personal Details</h4>
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-sm-6">
                                <div class="form-group">
                                    <input type="text" name="first_name" id="first_name" class="form-control input-lg" placeholder="First Name" tabindex="1" required="">
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="text" name="last_name" id="last_name" class="form-control input-lg" placeholder="Last Name" tabindex="2" required="">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <input type="text" name="mobile" id="mobile" pattern="[0-9]{10}" title="Please Enter 10 Numbers" class="form-control input-lg" placeholder="Mobile Number" tabindex="3" required="">
                        </div>
                        <div class="form-group">
                            <input type="email" name="email" id="email" class="form-control input-lg" placeholder="Email Address" tabindex="4" required="">
                        </div>
                        <hr class="divider h1">
                        <h4 style="color: #428bca">Working Details</h4>
                        <div class="row">
                            <div class="col-xs-12 col-sm-9 col-md-9">
                                <div class="form-group">
                                    <select id="hospital" name="hospital" class="form-control input-lg" >

                                    </select>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-3 col-md-3">
                                <div class="form-group">
                                    <input class="btn btn-sm btn-warning" data-toggle="modal" data-target="#newHospital" value="Not in List" type="button"/>
                                </div>
                            </div>
                        </div>
                        <hr class="divider h1">
                        <h4 style="color: #428bca">Payment Details <b><a class="h5" style="color: black;" href="#" data-toggle="modal" data-target="#whyPayment"><span class="glyphicon glyphicon-hand-left"></span>Why?</a></b></h4>
                        <div class="row">
                            <div class="col-xs-12 col-sm-9 col-md-9">
                                <div class="form-group">
                                    <select id="bank_name" name="bank" class="form-control input-lg" required="">

                                    </select>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-3 col-md-3">
                                <div class="form-group">
                                    <input class="btn btn-sm btn-warning" data-toggle="modal" data-target="#newBank" value="Not in List" type="button"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <input type="text" name="account_name" id="account_name" class="form-control input-lg" placeholder="Account Name" tabindex="3" required="">
                        </div>
                        <div class="form-group">
                            <input type="number" name="account_number" id="account_number" class="form-control input-lg" placeholder="Account Number" tabindex="3" required="">
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <p class="h5">Amount per one Health Instruction</p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6">
                                <div class="form-group input-group">
                                    <input type="number" id="tip_amount" name="tip_amount" class="form-control" required="" placeholder="0.00">
                                    <span class="input-group-addon">.LKR</span>
                                </div>
                                <p class="text-muted"><span class="glyphicon glyphicon-info-sign"></span>
                                <lable id="maintain"></lable>% will charge from every payment <br>
                                done by the patient as the site maintenance cost.
                                </p>
                            </div>
                        </div>
                        <hr class="divider h1">
                        <h4 style="color: #428bca">Create Security</h4>
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="password" name="password" pattern=".{5,10}" title="5 to 10 characters" id="password" class="form-control input-lg" placeholder="Password" tabindex="5" required="">
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="password" name="password_confirmation" pattern=".{5,10}" title="5 to 10 characters" id="password_confirmation" class="form-control input-lg" placeholder="Confirm Password" tabindex="6" required="">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6"></div>
                            <div class="col-xs-12 col-sm-6 col-md-6">
                                <p id="pass_not_match" class="text-danger hidden">
                                    <span class="glyphicon glyphicon-remove"></span> Password doesn't match
                                </p>
                                <p id="pass_match" class="text-success hidden">
                                    <span class="glyphicon glyphicon-ok"></span> Password match
                                </p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-4 col-sm-3 col-md-3">
                                <span class="button-checkbox">
                                    <button id="agree_box" value="1" type="button" class="btn" data-color="info" tabindex="7" >I Agree</button>
                                    <input type="checkbox" name="t_and_c" id="t_and_c" class="hidden" value="0">
                                </span>
                            </div>
                            <div class="col-xs-8 col-sm-9 col-md-9">
                                By clicking <strong class="label label-primary">Register</strong>, you agree to the 
                                <a href="#" data-toggle="modal" data-target="#t_and_c_m">Terms and Conditions</a> 
                                set out by this site, including our Cookie Use.
                            </div>
                        </div>

                        <hr class="colorgraph">
                        <div class="row">
                            <div class="col-xs-12 col-md-6"><input type="submit" id="reg" value="Register" class="btn btn-primary btn-block btn-lg" tabindex="7"></div>
                            <div class="col-xs-12 col-md-6"><a href="index.jsp" class="btn btn-success btn-block btn-lg">Sign In</a></div>
                        </div>
                    </form>
                </div>
            </div>
            <!--Term and Conditions-->
            <div class="modal fade" id="t_and_c_m" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-md">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Terms & Conditions</h4>
                        </div>
                        <div class="modal-body">
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                            tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                            quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                            consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                            cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
                            proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-dismiss="modal">I Agree</button>
                        </div>
                    </div>
                </div>
            </div>
            <!--Add New Hospital-->
            <div class="modal fade" id="newHospital" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Add New Hospital</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-sm-12">
                                    <p>Enter Hospital Name</p>
                                </div>
                                <div class="col-sm-12">
                                    <input class="form-control" type="text" id="new_hospital">
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="add_hospital" class="btn btn-success" >Add</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        </div>
                    </div>
                </div>
            </div>
            <!--Add new Bank-->
            <div class="modal fade" id="newBank" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Add New Bank</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-sm-12">
                                    <p>Enter Bank Name</p>
                                </div>
                                <div class="col-sm-12">
                                    <input class="form-control" type="text" id="new_bank">
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="add_bank" class="btn btn-success" >Add</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        </div>
                    </div>
                </div>
            </div>
            <!--Why Payment-->
            <div class="modal fade" id="whyPayment" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-md">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Why Payment Details</h4>
                        </div>
                        <div class="modal-body">
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                            tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                            quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                            consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                            cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
                            proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>    
        <div id="footer"></div>
    </body>
    <script type="text/javascript" src="js/signup.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/npm.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
</html>
