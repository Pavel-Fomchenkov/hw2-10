package pro.sky.javacoursepart2.hw29.service.departmentsService;

import pro.sky.javacoursepart2.hw29.model.Employee;

import java.util.List;
import java.util.Map;

public interface DepartmentsService {

    Map<Integer, List<Employee>> mapAllEmployeesByDepartment();

    List<Employee> listAllEmployeesSortedByDepartment();

    List<Employee> findEmployeesByDepartment(int department);

    Map<String, Employee> employeesWithSalaryLowerOrEqualThen(double salaryLimit);

    Map<String, Employee> employeesWithSalaryHigherOrEqualThen(int salaryLimit);

    Employee minSalaryEmployee();

    Employee minSalaryEmployee(int department);

    Employee maxSalaryEmployee();

    Employee maxSalaryEmployee(int department);

    double expencesSalary();

    double expencesSalary(int department);

    double averageSalary();

    double averageSalary(int department);

    Map<String, Employee> increaseSalary(double percent);

    Map<String, Employee> increaseSalary(int department, double percent);

    Employee increaseSalary(String firstName, String middleName, String lastName, double percent);
}
