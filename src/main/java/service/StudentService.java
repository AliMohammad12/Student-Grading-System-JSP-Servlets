package service;

import com.mysql.cj.conf.ConnectionUrlParser;
import dao.StudentDAO;
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
    public List<Student> findStudentsByCourseAndInstructor(int courseId, int instructorId) {
        return studentDao.findStudentsByCourseAndInstructor(courseId, instructorId);
    }
    public List<ConnectionUrlParser.Pair<ConnectionUrlParser.Pair<String, Integer>, Student>> findStudentsAndGradesByCourseAndInstructorId(int courseId, int instructorId) {
        return studentDao.findStudentsAndGradesByCourseAndInstructorId(courseId, instructorId);
    }
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }
    public void deleteStudent(int studentId) {
        studentDao.deleteStudent(studentId);
    }
}
