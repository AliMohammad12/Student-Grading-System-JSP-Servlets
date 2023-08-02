<%@ page import="model.Course" %>
<%@ page import="model.Instructor" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <style>
        td {
            height: 80px;
            width: 160px;
        }
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

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    if (session.getAttribute("student") == null) {
        response.sendRedirect("login");
    } else {
    Map<Course, List<Instructor>> allAvailableCourses = (Map<Course, List<Instructor>>) request.getAttribute("availableCourses");
%>

<section class="intro">
    <div class="bg-image h-100" style="background-color: #fff;">
        <div class="mask d-flex align-items-center h-100" style="background-color: rgba(0,0,0,.25);">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-12">
                        <div class="card bg-dark shadow-2-strong">
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-dark table-borderless mb-0">
                                        <caption style="caption-side: top; text-align: center; font-weight: bold; font-size: 18px; color: black;">Available Courses to enroll</caption>
                                        <br>
                                        <thead>
                                        <tr>
                                            <th style="text-align: center; vertical-align: middle;">ID</th>
                                            <th style="text-align: center; vertical-align: middle;">Course</th>
                                            <th style="text-align: center; vertical-align: middle;">Department</th>
                                            <th style="text-align: center; vertical-align: middle;">Course Instructors</th>
                                            <th style="text-align: center; vertical-align: middle;">Enroll</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <% for (Map.Entry<Course, List<Instructor>> entry : allAvailableCourses.entrySet()) {
                                            Course course = entry.getKey();
                                            List<Instructor> instructors = entry.getValue();
                                        %>
                                        <tr>
                                            <td style="text-align: center; vertical-align: middle;"><%= course.getCourseId() %></td>
                                            <td style="text-align: center; vertical-align: middle;"><%= course.getCourseName() %></td>
                                            <td style="text-align: center; vertical-align: middle;"><%= course.getDepartment().getName() %></td>
                                            <td style="text-align: center; vertical-align: middle;">
                                                <% for (Instructor instructor : instructors) { %>
                                                <li><%= instructor.toString() %></li>
                                                <% } %>
                                            </td>
                                            <td style="text-align: center; vertical-align: middle;">
                                                <% for (Instructor instructor : instructors) { %>
                                                <li>
                                                    <a href="student_enroll?instructorId=<%= instructor.getInstructorId() %>&courseId=<%= course.getCourseId() %>"
                                                       class="btn btn-primary btn-sm">Enroll</a>
                                                    <br>
                                                </li>
                                                <% } %>
                                            </td>
                                        </tr>
                                        <% } %>
                                        </tbody>

                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<% }%>
</body>
</html>
