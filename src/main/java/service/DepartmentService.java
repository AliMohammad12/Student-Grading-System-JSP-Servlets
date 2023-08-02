package service;

import dao.DepartmentDAO;
import model.Department;

import java.util.List;

public class DepartmentService {
    private DepartmentDAO departmentDao;
    public DepartmentService(DepartmentDAO departmentDao) {
        this.departmentDao = departmentDao;
    }
    public void createDepartment(Department department) {
        departmentDao.createDepartment(department);
    }
    public List<Department> getAllDepartments() {
        return departmentDao.getAllDepartments();
    }
    public void deleteDepartment(int departmentId) {
        departmentDao.deleteDepartment(departmentId);
    }
    public Department getDepartmentByName(String name) {
        return departmentDao.getDepartmentByName(name);
    }
}
