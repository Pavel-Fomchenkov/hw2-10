package pro.sky.javacoursepart2.hw29.employeeService;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pro.sky.javacoursepart2.hw29.exceptions.EmployeeAlreadyAddedException;
import pro.sky.javacoursepart2.hw29.exceptions.EmployeeNotFoundException;
import pro.sky.javacoursepart2.hw29.exceptions.EmployeeStorageIsFullException;
import pro.sky.javacoursepart2.hw29.model.Employee;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final int maxEmployees = 10;
    private final Map<String, Employee> employees = new HashMap<>();

    private String generateEmployeesMapKey(String lastName, String firstName, String middleName) {
        return lastName + firstName + middleName;
    }

    @Override
    public Map<String, Employee> getEmployees() {
        return Collections.unmodifiableMap(employees);
    }

    @Override
    public Employee addEmployee(String firstName, String middleName, String lastName, int department, double salary) {
        //              OLD METHOD
        if (employees.size() == maxEmployees) {
            throw new EmployeeStorageIsFullException("База данных переполнена.");
        }
        String key = generateEmployeesMapKey(lastName, firstName, middleName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException("В базе данных уже содержится сотрудник "
                    + firstName + " " + middleName + " " + lastName + ".");
        }
        Employee e = new Employee(firstName, middleName, lastName, department, salary);
        employees.put(key, e);
        return e;
    }

    @Override
    public Employee removeEmployee(String firstName, String middleName, String lastName) {
        String key = generateEmployeesMapKey(lastName, firstName, middleName);
        return employees.keySet().stream()
                .filter(k -> k.equals(key))
                .findAny()
                .map(k -> employees.remove(k))
                .orElseThrow(() -> new EmployeeNotFoundException("Cотрудник "
                        + firstName + " " + middleName + " " + lastName + " отсутвует в базе данных."));

// //              OLD METHOD
//        String key = generateEmployeesMapKey(lastName, firstName, middleName);
//        if (employees.containsKey(key)) {
//            return employees.remove(key);
//        }
//        throw new EmployeeNotFoundException("Cотрудник "
//        + firstName + " " + middleName + " " + lastName + " отсутвует в базе данных.");
    }

    @Override
    public Employee findEmployee(String firstName, String middleName, String lastName) {
        String key = generateEmployeesMapKey(lastName, firstName, middleName);
        return employees.entrySet().stream()
                .filter(e -> key.equals(e.getKey()))
                .map(e -> e.getValue())
                .findAny()
                .orElseThrow(() -> new EmployeeNotFoundException("Cотрудник "
                        + firstName + " " + middleName + " " + lastName + " отсутвует в базе данных."));

// //               VERSION 2
//        String key = generateEmployeesMapKey(lastName, firstName, middleName);
//        return employees.get(employees.keySet().stream()
//                .filter(e -> e.equals(key))
//                .findFirst()
//                .orElseThrow(() -> new EmployeeNotFoundException("Cотрудник "
//                + firstName + " " + middleName + " " + lastName + " отсутвует в базе данных.")));

// //               VERSION 3
//        return employees.values().stream()
//                .filter(e -> e.getFirstName().equals(firstName)
//                  && e.getMiddleName().equals(middleName)
//                  && e.getLastName().equals(lastName))
//                .findFirst()
//                .orElseThrow(() -> new EmployeeNotFoundException("Cотрудник "
//                + firstName + " " + middleName + " " + lastName + " отсутвует в базе данных."));

// //               VERSION 4
//        String key = generateEmployeesMapKey(lastName, firstName, middleName);
//        employees.entrySet().stream()
//                .filter(e -> key.equals(e.getKey()))
//                .map(e -> e.getValue())
//                .findFirst()
//                .orElseThrow(() -> new EmployeeNotFoundException("Cотрудник "
//                + firstName + " " + middleName + " " + lastName + " отсутвует в базе данных."));

// //               OLD METHOD
//        String key = generateEmployeesMapKey(lastName, firstName, middleName);
//        if (employees.containsKey(key)) {
//            return employees.get(key);
//        }
//        throw new EmployeeNotFoundException("Cотрудник "
//        + firstName + " " + middleName + " " + lastName + " отсутвует в базе данных.");
    }

    // Method should return List because the order of elements matter
    @Override
    public List<Employee> findAllEmployeesSortedByDepartment() {
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
    public Map<String, Employee> employeesWithSalaryLowerOrEqualThen(@RequestParam(value = "salary") double salaryLimit) {
        return employees.entrySet().stream()
                .filter(e -> e.getValue().getSalary() <= salaryLimit)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    @Override
    public Map<String, Employee> employeesWithSalaryHigherOrEqualThen(@RequestParam(value = "salary") int salaryLimit) {
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
        Employee e = findEmployee(firstName, middleName, lastName);
        e.setSalary(Math.round(employees.get(key).getSalary() * (100 + percent)) / 100.0);
        return e;
    }

    @Override
    public Employee editEmployee(String firstName, String middleName, String lastName, int department, double salary) {
        Employee e = findEmployee(firstName, middleName, lastName);
        e.setDepartment(department);
        e.setSalary(salary);
        return e;
    }

    @Override
    public String printNames() {
        StringBuilder sb = new StringBuilder();
        employees.values().stream()
                .forEach(e -> sb.append(e.getFirstName() + " " + e.getMiddleName() + " " + e.getLastName() + ", "));
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

}
