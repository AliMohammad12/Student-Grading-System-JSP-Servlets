package dao;

import model.Instructor;

import java.util.List;

public interface InstructorDAO {
    void createInstructor(Instructor instructor);
    Instructor getInstructorByAccountId(int accountId);
    List<Instructor> getAllInstructors();
    void deleteInstructor(int instructorId);
}
