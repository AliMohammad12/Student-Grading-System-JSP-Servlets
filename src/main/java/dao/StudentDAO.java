package dao;

import com.mysql.cj.conf.ConnectionUrlParser;
import model.Course;
import model.Instructor;
import model.Student;

import java.util.List;

public interface StudentDAO {
    void createStudent(Student student);
    Student getStudentByAccountId(int accountId);
    List<Student> getAllStudents();
    List<Student> findStudentsByCourseAndInstructor(int courseId, int instructorId);
    List<ConnectionUrlParser.Pair<ConnectionUrlParser.Pair<String, Integer>, Student>>   findStudentsAndGradesByCourseAndInstructorId(int courseId, int instructorId);
    void deleteStudent(int studentId);
}
