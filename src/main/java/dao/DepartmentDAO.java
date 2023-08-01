package dao;

import model.Department;

import java.util.List;

public interface DepartmentDAO {
    void createDepartment(Department department) throws ClassNotFoundException;
    Department getDepartmentById(int departmentId);
    List<Department> getAllDepartments() throws ClassNotFoundException;
    void updateDepartment(Department department);
    void deleteDepartment(int departmentId);
    public Department getDepartmentByName(String departmentName);
}
