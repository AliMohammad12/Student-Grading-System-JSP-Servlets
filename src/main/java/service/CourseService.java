package service;

import dao.CourseDAO;
import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CourseService {
    private CourseDAO courseDao;
    public CourseService(CourseDAO courseDao) {
        this.courseDao = courseDao;
    }
    public void createCourse(Course course) {
        courseDao.createCourse(course);
    }
    public Map<Course, List<Instructor>> getCoursesNotEnrolledByStudent(Student student) {
        return courseDao.getCoursesNotEnrolledByStudent(student);
    }
    public List<Course> getAllStudentCourses(Student student) {
        return courseDao.getAllStudentCourses(student);
    }
    public List<Course> getAllCourses() {
        return courseDao.getAllCourses();
    }
    public void updateStudentCourseGradeById(int courseEnrollmentId, String newGrade) {
        courseDao.updateStudentCourseGradeById(courseEnrollmentId, newGrade);
    }
    public void deleteStudentCourse(int studentId, int courseId) {
        courseDao.deleteStudentCourse(studentId, courseId);
    }
    public void enrollStudentInCourse(int courseId, int instructorId, int studentId) {
        courseDao.enrollStudentInCourse(courseId, instructorId, studentId);
    }
    public void assignCourseToInstructor(int courseId, int instructorId) {
        courseDao.assignCourseToInstructor(courseId, instructorId);
    }
    public void removeCourseFromInstructor(int courseId, int instructorId){
        courseDao.removeCourseFromInstructor(courseId, instructorId);
    }
    public List<Course> getAllInstructorCourses(Instructor instructor) {
        return courseDao.getAllInstructorCourses(instructor);
    }
    public List<Course> getUnassignedCoursesFromSameDept(Instructor instructor) {
        return courseDao.getUnassignedCoursesFromSameDept(instructor);
    }
    public void deleteStudentCourseById(int studentCourseId) {
        courseDao.deleteStudentCourseById(studentCourseId);
    }
    public StudentCourse getStudentCourseByStudentIdAndCourseId(int studentId, int courseId) throws SQLException, ClassNotFoundException {
        return courseDao.getStudentCourseByStudentIdAndCourseId(studentId, courseId);
    }
    public void deleteCourse(int courseId) {
        courseDao.deleteCourse(courseId);
    }
    public void deleteStudentCourseByStudentId(int studentId) {
        courseDao.deleteStudentCourseByStudentId(studentId);
    }
    public void deleteInstructorCoursesByInstructorId(int instructorId) {
        courseDao.deleteInstructorCoursesByInstructorId(instructorId);
    }
    public void deleteStudentCoursesByInstructorId(int instructorId) {
        courseDao.deleteStudentCoursesByInstructorId(instructorId);
    }
}
