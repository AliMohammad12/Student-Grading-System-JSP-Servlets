<%@ page import="model.Course" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

  <style>
    .navbar-inverse {
      background-color: #8B0000;
    }
    .navbar-inverse .navbar-nav > li > a {
      color: white;
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
            <li class="navbar-brand" style="font-weight: bold; color: white;">Welcome Instructor</li>
          </ul>
          <ul class="nav navbar-nav">
            <li><a href="instructor_courses"><span class="glyphicon glyphicon-menu-hamburger"></span> Courses</a></li>
            <li><a href="instructor_available_courses"><span class="glyphicon glyphicon-plus"></span> Teach New Course?</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="instructor_profile"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
            <li><a href="logout"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
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
  if (session.getAttribute("instructor") == null) {
    response.sendRedirect("login");
  } else {
  List<Course> availableCourses = (List<Course>)request.getAttribute("availableCourses");
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
                    <caption style="caption-side: top; text-align: center; font-weight: bold; font-size: 18px; color: black;"> Available Courses To Teach From Your Department </caption>
                    <br>
                    <thead>
                    <tr>
                      <th style="text-align: center; vertical-align: middle;">ID</th>
                      <th style="text-align: center; vertical-align: middle;">Course</th>
                      <th style="text-align: center; vertical-align: middle;">Department</th>
                      <th style="text-align: center; vertical-align: middle;">Teach</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                      for (Course course : availableCourses) {
                    %>
                    <tr>
                      <td style="text-align: center; vertical-align: middle;"><%= course.getCourseId() %></td>
                      <td style="text-align: center; vertical-align: middle;"><%= course.getCourseName() %></td>
                      <td style="text-align: center; vertical-align: middle;"><%= course.getDepartment().getName() %></td>
                      <td class="text-center align-middle">
                        <form action="teach_course" method="get">
                          <input type="hidden" name="courseId" value="<%= course.getCourseId() %>">
                          <button type="submit" class="btn btn-primary"> Teach </button>
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
<%} %>
</body>
</html>
