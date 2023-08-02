package daoimpl;

import dao.CourseDAO;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDAOImpl implements CourseDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_grading_system_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    public void createCourse(Course course) {
        String query = "INSERT INTO course (department_id, course_name) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, course.getDepartment().getId());
            statement.setString(2, course.getCourseName());

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Sorry, we encountered an issue while creating your request. Please try again later.");
        }
    }
    public Map<Course, List<Instructor>> getCoursesNotEnrolledByStudent(Student student) {
        String query = "SELECT " +
                "c.id AS Course_ID, " +
                "c.course_name AS Course_Name, " +
                "c.department_id AS Department_ID, " +
                "i.first_name AS First_Name, " +
                "i.last_name AS Last_Name, " +
                "i.email AS Instructor_Email, " +
                "d.department_name AS Department_Name, " +
                "i.account_id AS Account_ID, " +
                "i.id AS Instructor_ID " +
                "FROM course c " +
                "JOIN instructor_courses ic ON c.id = ic.course_id " +
                "JOIN instructor i ON ic.instructor_id = i.id " +
                "JOIN department d ON i.department_id = d.id " +
                "LEFT JOIN student_courses sc ON c.id = sc.course_id AND sc.student_id = ? " +
                "WHERE sc.id IS NULL";

        Map<Course, List<Instructor>> coursesWithInstructors = new HashMap<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, student.getStudentId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("Course_ID");
                String courseName = resultSet.getString("Course_Name");
                int departmentId = resultSet.getInt("Department_ID");
                String firstName = resultSet.getString("First_Name");
                String lastName = resultSet.getString("Last_Name");
                String instructorEmail = resultSet.getString("Instructor_Email");
                String departmentName = resultSet.getString("Department_Name");
                int accountId = resultSet.getInt("Account_ID");
                int instructorId = resultSet.getInt("Instructor_ID");

                Department department = new Department(departmentName);
                department.setId(departmentId);
                Instructor courseInstructor = new Instructor(firstName, lastName, department, instructorEmail);
                Course course = new Course(courseName, department);
                course.setCourseId(courseId);
                courseInstructor.setInstructorId(instructorId);

                if (coursesWithInstructors.containsKey(course)) {
                    List<Instructor> existingList = coursesWithInstructors.get(course);
                    existingList.add(courseInstructor);
                } else {
                    List<Instructor> newList = new ArrayList<>();
                    newList.add(courseInstructor);
                    coursesWithInstructors.put(course, newList);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return coursesWithInstructors;
    }

    public void enrollStudentInCourse(int courseId, int instructorId, int studentId) {
        String query = "INSERT INTO student_courses (student_id, course_id, instructor_id) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.setInt(3, instructorId);

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Sorry, we encountered an issue while creating your request. Please try again later.");
        }
    }
    public void deleteStudentCourse(int studentId, int courseId) {
        String query = "DELETE FROM student_courses WHERE student_id = ? AND course_id = ?";
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, studentId);
                statement.setInt(2, courseId);
                statement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public List<Course> getAllStudentCourses(Student student) {
        String query = "SELECT " +
                "c.id AS Course_ID, " +
                "c.course_name AS Course_Name, " +
                "c.department_id AS Department_ID, " +
                "d.department_name AS Department_Name " +
                "FROM student_courses sc " +
                "JOIN course c ON sc.course_id = c.id " +
                "JOIN department d ON c.department_id = d.id " +
                "WHERE sc.student_id = ?";
        List<Course> studentCourseList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, student.getStudentId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int courseId = resultSet.getInt("Course_ID");
                        String courseName = resultSet.getString("Course_Name");
                        int departmentId = resultSet.getInt("Department_ID");
                        String departmentName = resultSet.getString("Department_Name");

                        Department department = new Department(departmentName);
                        department.setId(departmentId);
                        Course course = new Course(courseName, department);
                        course.setCourseId(courseId);
                        studentCourseList.add(course);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return studentCourseList;
    }
    public List<Course> getAllInstructorCourses(Instructor instructor) {
        String query = "SELECT " +
                "c.id AS Course_ID, " +
                "c.course_name AS Course_Name, " +
                "c.department_id AS Department_ID, " +
                "d.department_name AS Department_Name " +
                "FROM instructor_courses ic " +
                "JOIN course c ON ic.course_id = c.id " +
                "JOIN department d ON c.department_id = d.id " +
                "WHERE ic.instructor_id = ?";
        List<Course> instructorCourses = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, instructor.getInstructorId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int courseId = resultSet.getInt("Course_ID");
                        String courseName = resultSet.getString("Course_Name");
                        int departmentId = resultSet.getInt("Department_ID");
                        String departmentName = resultSet.getString("Department_Name");
                        Department department = new Department(departmentName);
                        department.setId(departmentId);
                        Course course = new Course(courseName, department);
                        course.setCourseId(courseId);
                        instructorCourses.add(course);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return instructorCourses;
    }
    public List<Course> getUnassignedCoursesFromSameDept(Instructor instructor) {
        String query = "SELECT " +
                "c.id AS Course_ID, " +
                "c.course_name AS Course_Name, " +
                "c.department_id AS Department_ID, " +
                "d.department_name AS Department_Name " +
                "FROM course c " +
                "JOIN department d ON c.department_id = d.id " +
                "WHERE c.department_id = (SELECT department_id FROM instructor WHERE id = ?) " +
                "AND c.id NOT IN (SELECT course_id FROM instructor_courses WHERE instructor_id = ?)";
        List<Course> unassignedCourses = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                int instructorId = instructor.getInstructorId();
                statement.setInt(1, instructorId);
                statement.setInt(2, instructorId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int courseId = resultSet.getInt("Course_ID");
                        String courseName = resultSet.getString("Course_Name");
                        int departmentId = resultSet.getInt("Department_ID");
                        String departmentName = resultSet.getString("Department_Name");
                        Department department = new Department(departmentName);
                        department.setId(departmentId);
                        Course course = new Course(courseName, department);
                        course.setCourseId(courseId);
                        unassignedCourses.add(course);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return unassignedCourses;
    }
    public void assignCourseToInstructor(int courseId, int instructorId) {
        String query = "INSERT INTO instructor_courses (instructor_id, course_id) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1,instructorId);
            statement.setInt(2, courseId);

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Sorry, we encountered an issue while creating your request. Please try again later.");
        }
    }
    public void removeCourseFromInstructor(int courseId, int instructorId) {
        String query = "DELETE FROM instructor_courses WHERE instructor_id = ? AND course_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, instructorId);
            statement.setInt(2, courseId);

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void updateStudentCourseGradeById(int studentCourseId, String newGrade) {
        String query = "UPDATE student_courses SET grade = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newGrade);
            statement.setInt(2, studentCourseId);

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        String query = "SELECT c.id, c.department_id, c.course_name, d.department_name " +
                "FROM course c " +
                "JOIN department d ON c.department_id = d.id";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int departmentId = resultSet.getInt("department_id");
                String courseName = resultSet.getString("course_name");
                String departmentName = resultSet.getString("department_name");
                Department department = new Department(departmentName);
                department.setId(departmentId);
                Course course = new Course(courseName, department);
                course.setCourseId(id);
                courseList.add(course);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return courseList;
    }
    public StudentCourse getStudentCourseByStudentIdAndCourseId(int studentId, int courseId) throws SQLException, ClassNotFoundException {
        String query = "SELECT sc.id, i.id AS instructorId, i.email AS instructorEmail, " +
                "i.first_name AS instructorFirstName, i.last_name AS instructorLastName, " +
                "sc.grade, c.course_name, d.department_name " +
                "FROM student_courses sc " +
                "JOIN instructor_courses ic ON sc.instructor_id = ic.instructor_id AND sc.course_id = ic.course_id " +
                "JOIN instructor i ON ic.instructor_id = i.id " +
                "JOIN course c ON sc.course_id = c.id " +
                "JOIN department d ON c.department_id = d.id " +
                "WHERE sc.student_id = ? AND sc.course_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("IN");
                int id = resultSet.getInt("id");
                int instructorId = resultSet.getInt("instructorId");
                String instructorEmail = resultSet.getString("instructorEmail");
                String instructorFirstName = resultSet.getString("instructorFirstName");
                String instructorLastName = resultSet.getString("instructorLastName");
                String grade = resultSet.getString("grade");
                String courseName = resultSet.getString("course_name");
                String departmentName = resultSet.getString("department_name");

                Department department = new Department(departmentName);
                Instructor instructor = new Instructor(instructorFirstName, instructorLastName, department, instructorEmail);
                instructor.setInstructorId(instructorId);
                Course course = new Course(courseName, department);
                course.setCourseId(courseId);
                StudentCourse studentCourse = new StudentCourse(id, studentId, instructor, course, grade);
                return studentCourse;
            }
        }
        return null;
    }
    public void deleteCourse(int courseId) {
        String query = "DELETE FROM course WHERE id = ?";
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, courseId);
                statement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void deleteStudentCourseByStudentId(int studentId) {
        String query = "DELETE FROM student_courses WHERE student_id = ?";
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, studentId);
                statement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void deleteInstructorCoursesByInstructorId(int instructorId) {
        String query = "DELETE FROM instructor_courses WHERE instructor_id = ?";
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, instructorId);
                statement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void deleteStudentCoursesByInstructorId(int instructorId) {
        String query = "DELETE FROM student_courses WHERE instructor_id = ?";
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, instructorId);
                statement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void deleteStudentCourseById(int studentCourseId) {
        String query = "DELETE FROM student_courses WHERE id = ?";
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, studentCourseId);
                statement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
