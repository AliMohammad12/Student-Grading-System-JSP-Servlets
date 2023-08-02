<%@ page import="model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Department" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>All Courses</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand" href="admin_dashboard">Admin Dashboard</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ml-auto">
        <li class="nav-item">
          <a class="nav-link" href="logout">Logout</a>
        </li>
      </ul>
    </div>
  </div>
</nav>

<%
  response.setHeader("Cache-Control", "no-cache");
  response.setHeader("Cache-Control", "no-store");
  response.setHeader("Pragma", "no-cache");
  response.setDateHeader("Expires", 0);

  if (session.getAttribute("admin") == null) {
    response.sendRedirect("login");
  } else {
  List<Course> courseList = (List<Course>)request.getAttribute("courseList");
  List<Department> departmentList = (List<Department>) request.getAttribute("departmentList");
%>
<div class="container mt-4">
  <div class="row">
    <div class="col-md-6">
      <section class="intro">
        <div class="bg-image h-100" style="background-color: #fff;">
          <div class="mask d-flex align-items-center h-100" style="background-color: rgba(0,0,0,.25);">
            <div class="container">
              <div class="row justify-content-center">
                <div class="col-12">
                  <div class="card bg-dark text-white shadow-2-strong">
                    <div class="card-body">
                      <h5 class="card-title text-center">All Courses</h5>
                      <div class="table-responsive">
                        <table class="table table-dark table-borderless mb-0">
                          <thead>
                          <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Course</th>
                            <th scope="col">Department</th>
                            <th scope="col">Delete</th>
                          </tr>
                          </thead>
                          <tbody>
                          <% for (Course course : courseList) { %>
                          <tr>
                            <td><%= course.getCourseId() %></td>
                            <td><%= course.getCourseName() %></td>
                            <td><%= course.getDepartment().getName() %></td>
                            <td>
                              <form action="delete_course" method="get">
                                <input type="hidden" name="courseId" value="<%= course.getCourseId() %>">
                                <button type="submit" class="btn btn-danger">Delete</button>
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
    </div>
    <div class="col-md-6">
      <section class="add-course">
        <div class="container">
          <div class="card bg-dark text-white shadow-2-strong">
            <div class="card-body">
              <h5 class="card-title text-center">Add New Course</h5>
              <form action="add_course" method="get">
                <div class="form-group">
                  <label for="courseName">Course Name:</label>
                  <input type="text" class="form-control" id="courseName" name="courseName" required>
                </div>
                <div class="form-group">
                  <label class="form-label" for="courseDepartment">Department</label>
                  <select id="courseDepartment" name="courseDepartment" class="form-control form-control-lg">
                    <% for (Department department : departmentList) { %>
                    <option value="<%= department.getId() %>-<%= department.getName() %>"><%= department.getName() %></option>
                    <% } %>
                  </select>
                </div>
                <button type="submit" class="btn btn-primary">Add Course</button>
              </form>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<% } %>
</body>
</html>
