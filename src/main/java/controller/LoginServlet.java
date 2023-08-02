package controller;

import dao.AccountDAO;
import dao.InstructorDAO;
import dao.StudentDAO;
import daoimpl.AccountDAOImpl;
import daoimpl.InstructorDAOImpl;
import daoimpl.StudentDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Account;
import model.Instructor;
import model.Student;
import service.AccountService;
import service.InstructorService;
import service.StudentService;
import util.PasswordHasher;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private AccountService accountService;
    private StudentService studentService;
    private InstructorService instructorService;

    @Override
    public void init() {
        AccountDAO accountDao = new AccountDAOImpl();
        accountService = new AccountService(accountDao);

        InstructorDAO instructorDAO = new InstructorDAOImpl();
        instructorService = new InstructorService(instructorDAO);

        StudentDAO studentDAO = new StudentDAOImpl();
        studentService = new StudentService(studentDAO);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/login_page.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Account account = accountService.getAccountByEmail(email);
        int accountId = account.getId();
        if (isValidCredentials(account, password)) {
            String role = account.getRole();
            if (role.equals("ADMIN")) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", account);
                response.sendRedirect("admin_dashboard");
            } else if (role.equals("Student")) {
                Student student = studentService.getStudentByAccountId(accountId);
                HttpSession session = request.getSession();
                session.setAttribute("student", student);
                response.sendRedirect("student_courses");
            } else {
                Instructor instructor = instructorService.getInstructorByAccountId(accountId);
                HttpSession session = request.getSession();
                session.setAttribute("instructor", instructor);
                response.sendRedirect("instructor_courses");
            }
        } else {
            response.sendRedirect("login");
        }
    }
    private boolean isValidCredentials(Account account, String enteredPassword) {
        if (account == null) return false;
        return PasswordHasher.verifyPassword(account.getHashedPassword(), enteredPassword);
    }
}
