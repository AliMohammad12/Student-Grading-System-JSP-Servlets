package controller;

import com.mysql.cj.conf.ConnectionUrlParser;
import dao.CourseDAO;
import dao.StudentDAO;
import daoimpl.CourseDAOImpl;
import daoimpl.StudentDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Course;
import model.Instructor;
import model.Student;
import service.CourseService;
import service.StudentService;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/instructor_courses", "/instructor_course_details",
        "/withdraw_course", "/instructor_available_courses", "/instructor_teach", "/instructor_profile",
        "/instructor_logout", "/update_grade", "/remove_student", "/teach_course"})
public class InstructorServlet extends HttpServlet {
    private CourseService courseService;
    private StudentService studentService;

    @Override
    public void init() {
        CourseDAO courseDAO = new CourseDAOImpl();
        courseService = new CourseService(courseDAO);

        StudentDAO studentDAO = new StudentDAOImpl();
        studentService = new StudentService(studentDAO);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        System.out.println(action);
        switch (action) {
            case "/instructor_courses":
                showCourses(request, response);
                break;
            case "/instructor_course_details":
                viewCourseDetails(request, response);
                break;
            case "/withdraw_course":
                withdrawCourse(request, response);
                break;
            case "/update_grade":
                updateStudentGrade(request, response);
                break;
            case "/remove_student":
                removeStudent(request, response);
                break;
            case "/instructor_available_courses":
                viewAvailableCoursesToTeach(request, response);
                break;
            case "/teach_course":
                teachNewCourse(request, response);
                break;
            case "/instructor_profile":
                viewProfile(request, response);
                break;
        }
    }

    private void showCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Instructor instructor = (Instructor) session.getAttribute("instructor");
        List<Course> courseList = courseService.getAllInstructorCourses(instructor);
        request.setAttribute("instructor", instructor);
        request.setAttribute("courseList", courseList);
        request.getRequestDispatcher("/instructor_courses.jsp").forward(request, response);
    }


    private void viewCourseDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Instructor instructor = (Instructor) session.getAttribute("instructor");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int instructorId = Integer.parseInt(request.getParameter("instructorId"));

        List<ConnectionUrlParser.Pair<ConnectionUrlParser.Pair<String, Integer>, Student>>  studentListWithMarks = studentService.findStudentsAndGradesByCourseAndInstructorId(courseId, instructorId);
        request.setAttribute("courseId", courseId);
        request.setAttribute("instructorId", instructor);
        request.setAttribute("studentListWithMarks", studentListWithMarks);
        request.getRequestDispatcher("/instructor_course_details.jsp").forward(request, response);
    }

    private void updateStudentGrade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentCourseId = Integer.parseInt(request.getParameter("studentCourseId"));
        String newGrade = request.getParameter("grades");
        courseService.updateStudentCourseGradeById(studentCourseId, newGrade);
        response.sendRedirect("instructor_courses");
    }

    private void removeStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentCourseId = Integer.parseInt(request.getParameter("studentCourseId"));
        courseService.deleteStudentCourseById(studentCourseId);
        response.sendRedirect("instructor_courses");
    }
    private void viewAvailableCoursesToTeach(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Instructor instructor = (Instructor) session.getAttribute("instructor");
        List<Course> availableCourses = courseService.getUnassignedCoursesFromSameDept(instructor);
        request.setAttribute("availableCourses", availableCourses);
        request.getRequestDispatcher("/instructor_available_courses.jsp").forward(request, response);
    }
    private void teachNewCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Instructor instructor = (Instructor) session.getAttribute("instructor");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        courseService.assignCourseToInstructor(courseId, instructor.getInstructorId());
        response.sendRedirect("instructor_courses");
    }
    private void withdrawCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int instructorId = Integer.parseInt(request.getParameter("instructorId"));
        courseService.removeCourseFromInstructor(courseId, instructorId);
        response.sendRedirect("instructor_courses");
    }
    private void viewProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/instructor_profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
