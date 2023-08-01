package controller;
import dao.CourseDAO;
import daoimpl.CourseDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Course;
import model.Instructor;
import model.Student;
import model.StudentCourse;
import service.CourseService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/student_courses", "/student_course_details",
        "/student_withdraw_course", "/student_available_courses", "/student_enroll", "/student_profile"})
public class StudentServlet extends HttpServlet {
    private CourseService courseService;

    @Override
    public void init() {
        CourseDAO courseDAO = new CourseDAOImpl();
        courseService = new CourseService(courseDAO);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if (validate(request, response)) {
            response.sendRedirect("login");
        } else {
            switch (action) {
                case "/student_courses":
                    showCourses(request, response);
                    break;
                case "/student_course_details":
                    viewCourseDetails(request, response);
                    break;
                case "/student_withdraw_course":
                    withdrawCourse(request, response);
                    break;
                case "/student_available_courses":
                    showAvailableCourses(request, response);
                    break;
                case "/student_enroll":
                    enrollInACourse(request, response);
                    break;
                case "/student_profile":
                    viewProfile(request, response);
                    break;
            }
        }
    }
    private void showCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        List<Course> courseList;
        courseList = courseService.getAllStudentCourses(student);

        request.setAttribute("student", student);
        request.setAttribute("courseList", courseList);
        request.getRequestDispatcher("student_courses.jsp").forward(request, response);
    }

    private void viewCourseDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        StudentCourse studentCourse = null;
        try {
            studentCourse = courseService.getStudentCourseByStudentIdAndCourseId(studentId, courseId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("courseToView", studentCourse);
        request.getRequestDispatcher("/student_course_details.jsp").forward(request, response);
    }

    private void withdrawCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        courseService.deleteStudentCourse(studentId, courseId);
        response.sendRedirect("student_courses");
    }

    private void showAvailableCourses(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        Map<Course, List<Instructor>> allAvailableCourses = courseService.getCoursesNotEnrolledByStudent(student);
        request.setAttribute("availableCourses", allAvailableCourses);
        request.getRequestDispatcher("/student_available_courses.jsp").forward(request, response);
    }
    private void enrollInACourse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int instructorId = Integer.parseInt(request.getParameter("instructorId"));
        courseService.enrollStudentInCourse(courseId, instructorId, student.getStudentId());
        response.sendRedirect("student_courses");
    }

    private void viewProfile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        request.setAttribute("student", student);
        request.getRequestDispatcher("/student_profile.jsp").forward(request, response);
    }

    private boolean validate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("student") == null) {
            return true;
        }
        return false;
    }
}
