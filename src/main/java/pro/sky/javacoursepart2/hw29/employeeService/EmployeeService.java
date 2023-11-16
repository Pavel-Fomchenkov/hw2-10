package pro.sky.javacoursepart2.hw29.employeeService;

import pro.sky.javacoursepart2.hw29.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee addEmployee(String firstName, String middleName, String lastName, int department, double salary);

    Map<String, Employee> getEmployees();

    Employee removeEmployee(String lastName, String firstName, String middleName);

    Employee findEmployee(String firstName, String middleName, String lastName);

    List<Employee> findAllEmployeesSortedByDepartment();

    List <Employee> findEmployeesByDepartment(int department);

    Employee minSalaryEmployee();

    Employee minSalaryEmployee(int department);

    Employee maxSalaryEmployee();

    Employee maxSalaryEmployee(int department);

    double expencesSalary();

    double expencesSalary(int department);

    double averageSalary();

    double averageSalary(int department);

    Employee editEmployee(String firstName, String middleName, String lastName, int department, double salary);

    Map<String, Employee> increaseSalary(double percent);

    Map<String, Employee> increaseSalary(int department, double percent);

    Employee increaseSalary(String firstName, String middleName, String lastName, double percent);
}
