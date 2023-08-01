<%@ page import="model.StudentCourse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Details</title>
    <style>
        .course-info {
            background-color: #f9e9a5;
            padding: 10px;
            border-radius: 5px;
        }

        .instructor-info {
            background-color: #b2f2bb;
            padding: 10px;
            border-radius: 5px;
        }

        .grade-info {
            background-color: #b2ccff;
            padding: 10px;
            border-radius: 5px;
        }
    </style>
</head>
<body>

<div class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="navbar-collapse collapse" id="mobile_menu">
                    <ul class="nav navbar-nav">
                        <li><a href="student_courses"><span class="glyphicon glyphicon-menu-hamburger"></span> Courses </a></li>
                        <li><a href="student_available_courses"><span class="glyphicon glyphicon-plus"></span> Enroll New Course </a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="student_profile"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
                        <li><a href="student_logout"><span class="glyphicon glyphicon-log-in"></span> Logout <span></span></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<%
    StudentCourse studentCourse = (StudentCourse)request.getAttribute("courseToView");
%>


<div class="container mt-4">
    <h1>Course Details</h1>

    <div class="row">
        <div class="col-md-6">
            <div class="course-info">
                <h2>Course Information</h2>
                <p><strong>Course Name:</strong> <%= studentCourse.getCourse().getCourseName() %></p>
                <p><strong>Department:</strong> <%= studentCourse.getCourse().getDepartment().getName() %></p>
            </div>
        </div>
        <div class="col-md-6">
            <div class="instructor-info">
                <h2>Instructor Information</h2>
                <p><strong>Instructor Name:</strong> <%= studentCourse.getInstructor().getFirstName() %> <%= studentCourse.getInstructor().getLastName() %></p>
                <p><strong>Email:</strong> <%= studentCourse.getInstructor().getEmail() %></p>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="grade-info">
                <h2>Your Grade</h2>
                <p><strong>Grade:</strong> <%= studentCourse.getGrade() %></p>
            </div>
        </div>
    </div>
</div>


</body>
</html>
