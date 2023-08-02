package dao;

import model.Department;

import java.util.List;

public interface DepartmentDAO {
    void createDepartment(Department department);
    List<Department> getAllDepartments();
    void deleteDepartment(int departmentId);
    public Department getDepartmentByName(String departmentName);
}
