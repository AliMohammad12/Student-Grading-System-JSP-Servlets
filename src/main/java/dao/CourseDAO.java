package dao;
import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CourseDAO {
    void createCourse(Course course);
    Map<Course, List<Instructor>>  getCoursesNotEnrolledByStudent(Student student);
    List<Course> getAllStudentCourses(Student student);
    List<Course> getAllInstructorCourses(Instructor instructor);
    List<Course> getUnassignedCoursesFromSameDept(Instructor instructor);
    void assignCourseToInstructor(int courseId, int instructorId);
    void removeCourseFromInstructor(int courseId, int instructorId);
    List<Course> getAllCourses();
    StudentCourse getStudentCourseByStudentIdAndCourseId(int studentId, int courseId) throws SQLException, ClassNotFoundException;
    void updateStudentCourseGradeById(int studentCourseId, String newGrade);
    void deleteStudentCourse(int studentId, int courseId);
    void deleteCourse(int courseId);
    void deleteStudentCourseByStudentId(int studentId);
    void enrollStudentInCourse(int courseId, int instructorId, int studentId);
    void deleteStudentCourseById(int studentCourseId);
    void deleteInstructorCoursesByInstructorId(int instructorId);
    void deleteStudentCoursesByInstructorId(int instructorId);
}
