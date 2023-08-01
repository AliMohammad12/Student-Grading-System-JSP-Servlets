package controller;

import dao.AccountDAO;
import dao.DepartmentDAO;
import dao.InstructorDAO;
import dao.StudentDAO;
import daoimpl.AccountDAOImpl;
import daoimpl.DepartmentDAOImpl;
import daoimpl.InstructorDAOImpl;
import daoimpl.StudentDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Account;
import model.Department;
import model.Instructor;
import model.Student;
import service.AccountService;
import service.DepartmentService;
import service.InstructorService;
import service.StudentService;
import util.PasswordHasher;

import java.io.IOException;
import java.util.List;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    AccountService accountService;
    StudentService studentService;
    InstructorService instructorService;
    DepartmentService departmentService;
    @Override
    public void init() {
        AccountDAO accountDao = new AccountDAOImpl();
        accountService = new AccountService(accountDao);

        StudentDAO studentDAO = new StudentDAOImpl();
        studentService = new StudentService(studentDAO);

        InstructorDAO instructorDAO = new InstructorDAOImpl();
        instructorService = new InstructorService(instructorDAO);

        DepartmentDAO departmentDAO = new DepartmentDAOImpl();
        departmentService = new DepartmentService(departmentDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redirectToRegistrationPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        if (!isEmailValid(email)) {
            redirectToRegistrationPage(request, response);
            return;
        }
        registerAccount(email, password, role);
        int accountId = accountService.getAccountIdByEmail(email);
        if (role.equals("student")) {
            String firstName = request.getParameter("studentFirstName");
            String lastName = request.getParameter("studentLastName");
            String major = request.getParameter("studentMajor");
            int academicYear = Integer.parseInt(request.getParameter("studentAcademicYear"));
            Student student = new Student(firstName, lastName, major, academicYear, email, accountId);
            registerStudent(student);
        } else {
            String firstName = request.getParameter("instructorFirstName");
            String lastName = request.getParameter("instructorLastName");
            String departmentName = request.getParameter("instructorDepartment");
            Department department = departmentService.getDepartmentByName(departmentName);
            Instructor instructor = new Instructor(firstName, lastName, department, email, accountId);
            registerInstructor(instructor);
        }

        request.getRequestDispatcher("/login_page.jsp").forward(request, response);
    }
    private void redirectToRegistrationPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = null;
        try {
            departments = departmentService.getAllDepartments();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("departments", departments);
        request.getRequestDispatcher("/register_page.jsp").forward(request, response);
    }
    private void registerStudent(Student student) {
        studentService.createStudent(student);
    }
    private void registerInstructor(Instructor instructor){
        instructorService.createInstructor(instructor);
    }
    private void registerAccount(String email, String password, String role) {
        String hashedPassword = PasswordHasher.hashPassword(password);
        Account account = new Account(email, hashedPassword, role);
        accountService.createAccount(account);
    }
    private boolean isEmailValid(String email) {
        return !accountService.emailExists(email);
    }
}
