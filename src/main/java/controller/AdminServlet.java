package controller;

import dao.CourseDAO;
import dao.DepartmentDAO;
import dao.InstructorDAO;
import dao.StudentDAO;
import daoimpl.CourseDAOImpl;
import daoimpl.DepartmentDAOImpl;
import daoimpl.InstructorDAOImpl;
import daoimpl.StudentDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Course;
import model.Department;
import model.Instructor;
import model.Student;
import service.CourseService;
import service.DepartmentService;
import service.InstructorService;
import service.StudentService;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin_dashboard", "/viewAllCourses",
        "/viewAllInstructors", "/viewAllStudents", "/viewAllDepartments",
        "/delete_course", "/add_course", "/delete_department", "/add_department",
        "/delete_instructor", "/delete_student"})
   public class AdminServlet extends HttpServlet {
    private CourseService courseService;
    private StudentService studentService;
    private InstructorService instructorService;
    private DepartmentService departmentService;
    @Override
    public void init() {
        CourseDAO courseDAO = new CourseDAOImpl();
        courseService = new CourseService(courseDAO);

        StudentDAO studentDAO = new StudentDAOImpl();
        studentService = new StudentService(studentDAO);

        DepartmentDAO departmentDAO = new DepartmentDAOImpl();
        departmentService = new DepartmentService(departmentDAO);

        InstructorDAO instructorDAO = new InstructorDAOImpl();
        instructorService = new InstructorService(instructorDAO);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if (validate(request, response)) {
            response.sendRedirect("login");
        } else {
            switch (action) {
                case "/admin_dashboard":
                    showAdminDashboard(request, response);
                    break;
                case "/viewAllCourses":
                    viewAllCourses(request, response);
                    break;
                case "/delete_course":
                    deleteCourse(request, response);
                    break;
                case "/add_course":
                    addCourse(request, response);
                    break;
                case "/viewAllDepartments":
                    viewAllDepartments(request, response);
                    break;
                case "/add_department":
                    addDepartment(request, response);
                    break;
                case "/delete_department":
                    deleteDepartment(request, response);
                    break;
                case "/viewAllInstructors":
                    viewAllInstructors(request, response);
                    break;
                case "/delete_instructor":
                    deleteInstructor(request, response);
                    break;
                case "/viewAllStudents":
                    viewAllStudents(request, response);
                    break;
                case "/delete_student":
                    delete_student(request, response);
                    break;
            }
        }
    }
    private void showAdminDashboard(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/admin_dashboard.jsp").forward(request, response);
    }
    private void viewAllCourses(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Course> courseList = courseService.getAllCourses();
        List<Department> departmentList = departmentService.getAllDepartments();
        request.setAttribute("courseList", courseList);
        request.setAttribute("departmentList", departmentList);
        request.getRequestDispatcher("/admin_view_all_courses.jsp").forward(request, response);
    }
    private void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String courseName = (String)request.getParameter("courseName");
        String selectedDepartment = request.getParameter("courseDepartment");
        String[] departmentInfo = selectedDepartment.split("-");
        String departmentId = departmentInfo[0];
        String departmentName = departmentInfo[1];
        Department department = new Department(departmentName);
        department.setId(Integer.parseInt(departmentId));
        Course course = new Course(courseName, department);
        courseService.createCourse(course);
        response.sendRedirect("viewAllCourses");
    }
    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        courseService.deleteCourse(courseId);
        response.sendRedirect("viewAllCourses");
    }
    private void viewAllDepartments(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Department> departmentList = departmentService.getAllDepartments();
        request.setAttribute("departmentList", departmentList);
        request.getRequestDispatcher("/admin_view_all_departments.jsp").forward(request, response);
    }
    private void addDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String departmentName = request.getParameter("departmentName");
        Department department = new Department(departmentName);
        departmentService.createDepartment(department);
        response.sendRedirect("viewAllDepartments");
    }
    private void deleteDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int departmentId = Integer.parseInt(request.getParameter("departmentId"));
        departmentService.deleteDepartment(departmentId);
        response.sendRedirect("viewAllDepartments");
    }
    private void viewAllInstructors(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Instructor> instructorList = instructorService.getAllInstructors();
        request.setAttribute("instructorList", instructorList);
        request.getRequestDispatcher("/admin_view_all_instructors.jsp").forward(request, response);
    }
    private void deleteInstructor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int instructorId = Integer.parseInt(request.getParameter("instructorId"));
        instructorService.deleteInstructor(instructorId);
        response.sendRedirect("viewAllInstructors");
    }
    private void viewAllStudents(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Student> studentList = studentService.getAllStudents();
        request.setAttribute("studentList", studentList);
        request.getRequestDispatcher("/admin_view_all_students.jsp").forward(request, response);
    }
    private void delete_student(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        studentService.deleteStudent(studentId);
        response.sendRedirect("viewAllStudents");
    }
    private boolean validate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            return true;
        }
        return false;
    }
}
