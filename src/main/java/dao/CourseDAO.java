package dao;
import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CourseDAO {
    void createCourse(Course course);
    Course getCourseById(int courseId);
    Map<Course, List<Instructor>>  getCoursesNotEnrolledByStudent(Student student);
//    List<StudentCourse> getStudentCourses(Student student);
    List<Course> getAllStudentCourses(Student student);
    List<Course> getAllInstructorCourses(Instructor instructor);
    List<Course> getUnassignedCoursesFromSameDept(Instructor instructor);
    void assignCourseToInstructor(int courseId, int instructorId);
    void removeCourseFromInstructor(int courseId, int instructorId);
    List<Course> getAllCourses();
    List<Course> getCoursesByDepartment(int departmentId);
//    StudentCourse getStudentCourse(Student student, Course course, Instructor instructor); // course enrollment

    StudentCourse getStudentCourseByStudentIdAndCourseId(int studentId, int courseId) throws SQLException, ClassNotFoundException;
    void updateStudentCourseGradeById(int studentCourseId, String newGrade);

    void deleteStudentCourse(int studentId, int courseId);
    void updateCourse(Course course);
    void deleteCourse(int courseId);
    void deleteStudentCourseByStudentId(int studentId);
    void enrollStudentInCourse(int courseId, int instructorId, int studentId);
    void deleteStudentCourseById(int studentCourseId);
    //    public List<StudentCourse> getStudentCoursesByStudentId(int studentId) throws SQLException, ClassNotFoundException;
    void deleteInstructorCoursesByInstructorId(int instructorId);
    void deleteStudentCoursesByInstructorId(int instructorId);
}
