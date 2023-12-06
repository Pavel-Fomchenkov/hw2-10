package pro.sky.javacoursepart2.hw210.service.employeeService;

import pro.sky.javacoursepart2.hw210.model.Employee;

import java.util.Map;

public interface EmployeeService {
    Employee addEmployee(String firstName, String middleName, String lastName, int department, double salary);

    Map<String, Employee> getEmployees();

    Employee removeEmployee(String firstName, String middleName, String lastleName);

    Employee findEmployee(String firstName, String middleName, String lastName);

    Employee editEmployee(String firstName, String middleName, String lastName, int department, double salary);

    String printNames();
}
