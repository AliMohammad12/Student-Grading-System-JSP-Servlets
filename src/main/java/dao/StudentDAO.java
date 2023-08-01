package dao;

import com.mysql.cj.conf.ConnectionUrlParser;
import model.Course;
import model.Instructor;
import model.Student;

import java.util.List;

public interface StudentDAO {
    void createStudent(Student student);
    Student getStudentByAccountId(int accountId);
    Student getStudentById(int studentId);
    List<Student> getAllStudents();
    List<Student> findStudentsByCourseAndInstructor(int courseId, int instructorId);
    List<ConnectionUrlParser.Pair<ConnectionUrlParser.Pair<String, Integer>, Student>>   findStudentsAndGradesByCourseAndInstructorId(int courseId, int instructorId);

    List<Student> getStudentsByMajor(String major);
    void updateStudent(Student student);
    void deleteStudent(int studentId);
}
