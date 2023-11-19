package pro.sky.javacoursepart2.hw29.service.employeeService;

import pro.sky.javacoursepart2.hw29.model.Employee;

import java.util.Map;

public interface EmployeeService {
    Employee addEmployee(String firstName, String middleName, String lastName, int department, double salary);

    Map<String, Employee> getEmployees();

    Employee removeEmployee(String lastName, String firstName, String middleName);

    Employee findEmployee(String firstName, String middleName, String lastName);

    Employee editEmployee(String firstName, String middleName, String lastName, int department, double salary);

    String printNames();
}
