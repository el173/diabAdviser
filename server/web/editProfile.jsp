<%-- 
    Document   : editProfile
    Created on : 14-Nov-2015, 15:14:04
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
        <script type="text/javascript">
            $(document).ready(function () {
                if (getUrlParameter('msg') === 'ok') {
                    alert('User details updated !!');
                    window.location.href = 'http://localhost:8080/diabAdviser/home.jsp';
                }
                $('#btn_change').addClass('disabled');
                getHospitals();
                getBanks();
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
                $.post('getSelectedHospital', {}, function (data, status) {
                    $('#hospital').val(data);
                });
                $.post('getSelectedBank', {}, function (data, status) {
                    $('#bank_name').val(data);
                });
                $('#btn_change').click(function () {
                    var crnt_pass = $('#crntpass').val();
                    var new_pass = $('#crnwpass').val();
                    if (crnt_pass.trim() === '' || new_pass.trim() === '') {
                        alert('Please enter current password');
                    } else {
                        if (crnt_pass === new_pass) {
                            alert('New password should not previous password');
                            $('#crnwpass').val('');
                            $('#pass').val('');
                        } else {
                            if (new_pass.length < 5) {
                                alert('Password must contain more than 5 characters');
                            } else {
                                $.post('updatePassword', {
                                    c_pass: crnt_pass,
                                    n_pass: new_pass
                                }, function (data, status) {
                                    if (data === '1') {
                                        alert('Password Changed please sign in againg');
                                        window.location.href = 'http://localhost:8080/diabAdviser/logout';
                                    } else {
                                        alert('Current Password does not match');
                                    }
                                });
                            }
                        }
                        $('#pass_match').addClass('hidden');
                        $('#pass_not_match').addClass('hidden');
                        $('#btn_change').attr('disabled', '');
                        $('#crnwpass').val('');
                        $('#pass').val('');
                    }
                });
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
                        $('#hospital').append($('<option></option>').val(id + 1).html(value));
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
                        $('#bank_name').append($('<option></option>').val(id + 1).html(value));
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
        <title>User Settings</title>
    </head>
    <body>
        <br><br>
        <div class="row"><div class="col-md-8 col-md-offset-5">
                <div class="col-lg-8 ">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <p class="h4 text-center"> <span class="glyphicon glyphicon-user"></span> Update User Profile</p>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" role="form" id="frm" action="updateProfile" method="post">
                                <div class="row">
                                    <div class="col-md-6"><p><b>First Name</b></p></div>
                                    <div class="col-md-6"><p><b>Last Name</b></p></div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <input type="text" name="firstname" class="form-control" required="" value="${requestScope.f_name}">
                                    </div>
                                    <div class="col-md-6">
                                        <input type="text" name="lastname" class="form-control" required="" value="${requestScope.l_name}">
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-12">
                                        <p><b>E-mail</b></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <input type="mail" name="docmail" class="form-control" disabled="" required="" value="${requestScope.email}">
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-12">
                                        <p><b>Mobile</b></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <input type="number" name="mobile" class="form-control" required="" value="${requestScope.mob}">
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-12">
                                        <p><b>Hospital</b></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <select id="hospital" name="hospital" class="form-control input-sm" required="">

                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <input class="btn btn-sm btn-warning" data-toggle="modal" data-target="#newHospital" value="Not in List" type="button"/>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-12">
                                        <p><b>Bank</b></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <select id="bank_name" name="bank" class="form-control input-sm" required="">

                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <input class="btn btn-sm btn-warning" data-toggle="modal" data-target="#newBank" value="Not in List" type="button"/>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-12">
                                        <p><b>Account Name</b></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <input type="text" name="accname" class="form-control" required="" value="${requestScope.acc_name}">
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-12">
                                        <p><b>Account Number</b></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <input type="text" name="accnum" class="form-control" required="" value="${requestScope.acc_num}">
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-12">
                                        <p><b>Tip Payment Amount (LKR)</b></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <input type="number" name="tip" class="form-control" required="" value="${requestScope.tip_payment}">
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-12">
                                        <p><b>Password</b></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <input type="password" name="c_pass" class="form-control" required="">
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-6"></div>
                                    <div class="col-md-6 text-right">
                                        <button type="submit" class="btn btn-success">Update</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="panel-footer">
                            <div class="row">
                                <div class="col-lg-12">
                                    <p class="text-danger">
                                        <a href="home.jsp">
                                            <span class="glyphicon glyphicon-backward"></span> click here
                                        </a> to go back
                                        or 
                                        <a href="" id="chng_pass" data-toggle="modal" data-target="#changePass">
                                            <span class="glyphicon glyphicon-lock"></span> click here
                                        </a> 
                                        to change password
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Change Password -->
        <div class="modal fade" id="changePass" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-lock"></span> Change Password</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-6">
                                <b><p>Current Password</p></b>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <input type="password" id="crntpass" required="" class="form-control"/>
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
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button id="btn_change" class="btn btn-success" disabled="">Change Password</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
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
        <div class="row">        
            <div id="footer"></div>
        </div>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
    </body>
</html>
