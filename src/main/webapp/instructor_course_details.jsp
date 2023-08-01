<%@ page import="model.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mysql.cj.conf.ConnectionUrlParser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="navbar-collapse collapse" id="mobile_menu">
                    <ul class="nav navbar-nav">
                        <li><a href="instructor_courses"><span class="glyphicon glyphicon-menu-hamburger"></span> Courses </a></li>
                        <li><a href="instructor_available_courses"><span class="glyphicon glyphicon-plus"></span>  Teach New Course ? </a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="instructor_profile"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
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
    if (session.getAttribute("instructor") == null) {
        response.sendRedirect("login");
    } else {
    List<ConnectionUrlParser.Pair<ConnectionUrlParser.Pair<String, Integer>, Student>>
            studentListWithMarks  = (List<ConnectionUrlParser.Pair<ConnectionUrlParser.Pair<String, Integer>, Student>>)
            request.getAttribute("studentListWithMarks");

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
                                        <caption style="caption-side: top; text-align: center; font-weight: bold; font-size: 18px;">Student Enrolled In The Course </caption>
                                        <br>
                                        <thead>
                                        <tr>
                                            <th style="text-align: center; vertical-align: middle;">ID</th>
                                            <th style="text-align: center; vertical-align: middle;">First name</th>
                                            <th style="text-align: center; vertical-align: middle;">Last name</th>
                                            <th style="text-align: center; vertical-align: middle;">Email</th>
                                            <th style="text-align: center; vertical-align: middle;">Grade</th>
                                            <th style="text-align: center; vertical-align: middle;">Remove Student</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <% for (ConnectionUrlParser.Pair<ConnectionUrlParser.Pair<String, Integer>, Student> pair : studentListWithMarks) {
                                            String grade = pair.left.left;
                                            int studentCourseId = pair.left.right;
                                            Student student = pair.right;
                                        %>
                                        <tr>
                                            <td><%= student.getStudentId() %></td>
                                            <td><%= student.getFirstName() %></td>
                                            <td><%= student.getLastName() %></td>
                                            <td><%= student.getEmail() %></td>
                                            <td>
                                                <form action="update_grade" method="get">
                                                    <input type="hidden" name="studentCourseId" value="<%= studentCourseId %>">
                                                    <select name="grades" onchange="this.form.submit()">
                                                        <option value="A+" <%= grade.equals("A+") ? "selected" : "" %>>A+</option>
                                                        <option value="A" <%= grade.equals("A") ? "selected" : "" %>>A</option>
                                                        <option value="A-" <%= grade.equals("A-") ? "selected" : "" %>>A-</option>
                                                        <option value="B+" <%= grade.equals("B+") ? "selected" : "" %>>B+</option>
                                                        <option value="B" <%= grade.equals("B") ? "selected" : "" %>>B</option>
                                                        <option value="B-" <%= grade.equals("B-") ? "selected" : "" %>>B-</option>
                                                        <option value="C+" <%= grade.equals("C+") ? "selected" : "" %>>C+</option>
                                                        <option value="C" <%= grade.equals("C") ? "selected" : "" %>>C</option>
                                                        <option value="C-" <%= grade.equals("C-") ? "selected" : "" %>>C-</option>
                                                        <option value="D+" <%= grade.equals("D+") ? "selected" : "" %>>D+</option>
                                                        <option value="D" <%= grade.equals("D") ? "selected" : "" %>>D</option>
                                                        <option value="D-" <%= grade.equals("D-") ? "selected" : "" %>>D-</option>
                                                        <option value="F" <%= grade.equals("F") ? "selected" : "" %>>F</option>
                                                        <option value="Ungraded" <%= grade.equals("Ungraded") ? "selected" : "" %>>UNGRADED</option>
                                                    </select>
                                                </form>
                                            </td>
                                            <td>
                                                <form action="remove_student" method="get">
                                                    <input type="hidden" name="studentCourseId" value="<%= studentCourseId %>">
                                                    <button type="submit" class="btn btn-danger">Remove</button>
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
<% }%>

</body>
</html>
