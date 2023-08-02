<%@ page import="model.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Profile </title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <style>
        .navbar-inverse {
            background-color: #1E3859;
        }

        .navbar-inverse .navbar-nav > li > a {
            color: #EAEAEA;
        }

        .navbar-text.welcome-message {
            color: white;
            font-weight: bold;
        }
    </style>
</head>
<body>

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    if (session.getAttribute("student") == null) {
        response.sendRedirect("login");
    } else {
    Student student = (Student)session.getAttribute("student");

%>
<div class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="navbar-collapse collapse" id="mobile_menu">
                    <ul class="nav navbar-nav">
                        <li class="navbar-brand" style="font-weight: bold; color: white;">Welcome Student</li>
                    </ul>

                    <ul class="nav navbar-nav">
                        <li><a href="student_courses"><span class="glyphicon glyphicon-menu-hamburger"></span> Courses </a></li>
                        <li><a href="student_available_courses"><span class="glyphicon glyphicon-plus"></span> Enroll New Course </a></li>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="student_profile"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
                        <li><a href="logout"><span class="glyphicon glyphicon-log-in"></span> Logout <span></span></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container mt-4">
    <div class="card">
        <div class="card-header bg-primary text-white">
            <h3 class="mb-0">Student Information</h3>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="firstName">First Name:</label>
                        <input type="text" id="firstName" class="form-control" value="<%= student.getFirstName() %>" readonly>
                    </div>
                    <div class="form-group">
                        <label for="lastName">Last Name:</label>
                        <input type="text" id="lastName" class="form-control" value="<%= student.getLastName() %>" readonly>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" class="form-control" value="<%= student.getEmail() %>" readonly>
                    </div>
                    <div class="form-group">
                        <label for="major">Major:</label>
                        <input type="text" id="major" class="form-control" value="<%= student.getMajor() %>" readonly>
                    </div>
                    <div class="form-group">
                        <label for="academicYear">Academic Year:</label>
                        <input type="text" id="academicYear" class="form-control" value="<%= student.getAcademicYear() %>" readonly>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<% }%>
</body>
</html>
