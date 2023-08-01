<%@ page import="model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<%  List<Course> courseList = (List<Course>) request.getAttribute("courseList");
    Student student = (Student) request.getAttribute("student");
%>

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
                                        <caption style="caption-side: top; text-align: center; font-weight: bold; font-size: 18px;">Your Courses List</caption>
                                        <thead>
                                        <tr>
                                            <th scope="col"> ID </th>
                                            <th scope="col">Course</th>
                                            <th scope="col">Department</th>
                                            <th scope="col">Course Details</th>
                                            <th scope="col">Withdraw</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <% for (Course course : courseList) { %>
                                        <tr>
                                            <td><%= course.getCourseId() %></td>
                                            <td><%= course.getCourseName() %></td>
                                            <td><%= course.getDepartment().getName() %></td>
                                            <td>
                                                <form action="student_course_details" method="get">
                                                    <input type="hidden" name="courseId" value="<%= course.getCourseId() %>">
                                                    <input type="hidden" name="studentId" value="<%= student.getStudentId() %>">
                                                    <button type="submit" class="btn btn-primary">View Details</button>
                                                </form>
                                            </td>
                                            <td>
                                                <form action="student_withdraw_course" method="get" onsubmit="return confirm('Are you sure you want to withdraw from this course?');">
                                                    <input type="hidden" name="courseId" value="<%= course.getCourseId() %>">
                                                    <input type="hidden" name="studentId" value="<%= student.getStudentId() %>">
                                                    <button type="submit" class="btn btn-danger">Withdraw</button>
                                                </form>
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

</body>
</html>
