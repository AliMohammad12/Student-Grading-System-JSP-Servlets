package service;

import dao.DepartmentDAO;
import model.Department;

import java.util.List;

public class DepartmentService {
    private DepartmentDAO departmentDao;

    public DepartmentService(DepartmentDAO departmentDao) {
        this.departmentDao = departmentDao;
    }
    public void createDepartment(Department department) throws ClassNotFoundException {
        departmentDao.createDepartment(department);
    }
    public Department getDepartmentById(int departmentId) {
        return departmentDao.getDepartmentById(departmentId);
    }

    public List<Department> getAllDepartments() throws ClassNotFoundException {
        return departmentDao.getAllDepartments();
    }

    public void updateDepartment(Department department) {
        departmentDao.updateDepartment(department);
    }
    public void deleteDepartment(int departmentId) {
        departmentDao.deleteDepartment(departmentId);
    }

    public Department getDepartmentByName(String name) {
        return departmentDao.getDepartmentByName(name);
    }
}
