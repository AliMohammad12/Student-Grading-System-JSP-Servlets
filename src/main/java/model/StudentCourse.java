package model;

public class StudentCourse {
    private int id;
    private int studentId;
    private Instructor instructor;
    private Course course;
    private String grade;

    public StudentCourse(int id, int studentId, Instructor instructor, Course course, String grade) {
        this.id = id;
        this.studentId = studentId;
        this.instructor = instructor;
        this.course = course;
        this.grade = grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}
