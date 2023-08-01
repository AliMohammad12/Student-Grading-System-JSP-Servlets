package service;

import com.mysql.cj.conf.ConnectionUrlParser;
import dao.StudentDAO;
import model.Course;
import model.Instructor;
import model.Student;

import java.util.List;

public class StudentService {
    private StudentDAO studentDao;
    public StudentService(StudentDAO studentDao) {
        this.studentDao = studentDao;
    }
    public void createStudent(Student student) {
        studentDao.createStudent(student);
    }
    public Student getStudentByAccountId(int accountId) {
        return studentDao.getStudentByAccountId(accountId);
    }
    public Student getStudentById(int studentId) {
        return studentDao.getStudentById(studentId);
    }
    public List<Student> findStudentsByCourseAndInstructor(int courseId, int instructorId) {
        return studentDao.findStudentsByCourseAndInstructor(courseId, instructorId);
    }
    public List<ConnectionUrlParser.Pair<ConnectionUrlParser.Pair<String, Integer>, Student>> findStudentsAndGradesByCourseAndInstructorId(int courseId, int instructorId) {
        return studentDao.findStudentsAndGradesByCourseAndInstructorId(courseId, instructorId);
    }
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }
    public List<Student> getStudentsByMajor(String major) {
        return studentDao.getStudentsByMajor(major);
    }
    public void updateStudent(Student student) {
        studentDao.updateStudent(student);
    }
    public void deleteStudent(int studentId) {
        studentDao.deleteStudent(studentId);
    }
}
