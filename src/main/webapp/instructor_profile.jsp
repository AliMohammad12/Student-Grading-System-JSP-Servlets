<%@ page import="model.Instructor" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instructor Profile</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<div class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="navbar-collapse collapse" id="mobile_menu">
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
    Instructor instructor = (Instructor) request.getSession().getAttribute("instructor");
%>

<div class="container mt-4">
    <div class="card">
        <div class="card-header bg-primary text-white">
            <h3 class="mb-0">Instructor Information</h3>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="firstName">First Name:</label>
                        <input type="text" id="firstName" class="form-control" value="<%= instructor.getFirstName() %>" readonly>
                    </div>
                    <div class="form-group">
                        <label for="lastName">Last Name:</label>
                        <input type="text" id="lastName" class="form-control" value="<%= instructor.getLastName() %>" readonly>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" class="form-control" value="<%= instructor.getEmail() %>" readonly>
                    </div>
                    <div class="form-group">
                        <label for="major">Department:</label>
                        <input type="text" id="major" class="form-control" value="<%= instructor.getDepartment().getName() %>" readonly>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
