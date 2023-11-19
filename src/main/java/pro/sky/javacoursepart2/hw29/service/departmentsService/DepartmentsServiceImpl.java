package pro.sky.javacoursepart2.hw29.service.departmentsService;

import org.springframework.stereotype.Service;
import pro.sky.javacoursepart2.hw29.exceptions.EmployeeNotFoundException;
import pro.sky.javacoursepart2.hw29.model.Employee;
import pro.sky.javacoursepart2.hw29.model.EmployeeStorage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentsServiceImpl implements DepartmentsService {

    private final Map<String, Employee> employees = EmployeeStorage.employees;

    private String generateEmployeesMapKey(String lastName, String firstName, String middleName) {
        return EmployeeStorage.generateEmployeesMapKey(lastName, firstName, middleName);
    }

    @Override
    public Map<Integer, List<Employee>> mapAllEmployeesByDepartment() {
        return employees.values().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    @Override
    public List<Employee> listAllEmployeesSortedByDepartment() {
        return employees.values().stream()
                .sorted(Comparator.comparingInt(employee -> employee.getDepartment()))
                .toList();
    }

    @Override
    public List<Employee> findEmployeesByDepartment(int department) {
        return employees.values().stream()
                .filter(e -> e.getDepartment() == department)
                .toList();
    }

    @Override
    public Map<String, Employee> employeesWithSalaryLowerOrEqualThen(double salaryLimit) {
        return employees.entrySet().stream()
                .filter(e -> e.getValue().getSalary() <= salaryLimit)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    @Override
    public Map<String, Employee> employeesWithSalaryHigherOrEqualThen(int salaryLimit) {
        return employees.entrySet().stream()
                .filter(e -> e.getValue().getSalary() >= salaryLimit)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    @Override
    public Employee minSalaryEmployee() {
        return employees.values().stream()
                .min(Comparator.comparingDouble(employee -> employee.getSalary()))
                .orElseThrow();
    }

    @Override
    public Employee minSalaryEmployee(int department) {
        return findEmployeesByDepartment(department).stream()
                .min(Comparator.comparingDouble(employee -> employee.getSalary()))
                .orElseThrow();
    }

    @Override
    public Employee maxSalaryEmployee() {
        return employees.values().stream()
                .max(Comparator.comparingDouble(employee -> employee.getSalary()))
                .orElseThrow();
    }

    @Override
    public Employee maxSalaryEmployee(int department) {
        return findEmployeesByDepartment(department).stream()
                .max(Comparator.comparingDouble(employee -> employee.getSalary()))
                .orElseThrow();
    }

    @Override
    public double expencesSalary() {
        return employees.values().stream()
                .map(employee -> employee.getSalary())
                .reduce(0.0, (a, b) -> a + b);
    }

    @Override
    public double expencesSalary(int department) {
        return findEmployeesByDepartment(department).stream()
                .map(employee -> employee.getSalary())
                .reduce(0.0, (a, b) -> a + b);
    }

    @Override
    public double averageSalary() {
        return employees.values().stream()
                .mapToDouble(employee -> employee.getSalary())
                .average().orElseThrow(() -> new EmployeeNotFoundException("Сотрудники для расчета не найдены в базе данных."));
    }

    @Override
    public double averageSalary(int department) {
        return findEmployeesByDepartment(department).stream()
                .mapToDouble(employee -> employee.getSalary())
                .average().orElseThrow(() -> new EmployeeNotFoundException("Сотрудники для расчета в отделе " + department + " не найдены."));
    }

    @Override
    public Map<String, Employee> increaseSalary(double percent) {
        employees.values()
                .forEach(e -> e.setSalary(Math.round(e.getSalary() * (100 + percent)) / 100.0));
        return Collections.unmodifiableMap(employees);
    }

    @Override
    public Map<String, Employee> increaseSalary(int department, double percent) {
        employees.values().stream()
                .filter(e -> e.getDepartment() == department)
                .forEach(e -> e.setSalary(Math.round(e.getSalary() * (100 + percent)) / 100.0)); // RETURNS VOID
        return employees.entrySet().stream()
                .filter(e -> e.getValue().getDepartment() == department)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    @Override
    public Employee increaseSalary(String firstName, String middleName, String lastName, double percent) {
        String key = generateEmployeesMapKey(lastName, firstName, middleName);
        Employee employee = employees.entrySet().stream()
                .filter(e -> key.equals(e.getKey()))
                .map(e -> e.getValue())
                .findAny()
                .orElseThrow(() -> new EmployeeNotFoundException("Cотрудник "
                        + firstName + " " + middleName + " " + lastName + " отсутвует в базе данных."));
        employee.setSalary(Math.round(employees.get(key).getSalary() * (100 + percent)) / 100.0);
        return employee;
    }


}
