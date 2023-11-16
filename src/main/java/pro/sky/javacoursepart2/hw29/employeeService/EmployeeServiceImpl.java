package pro.sky.javacoursepart2.hw29.employeeService;

import org.springframework.stereotype.Service;
import pro.sky.javacoursepart2.hw29.exceptions.EmployeeAlreadyAddedException;
import pro.sky.javacoursepart2.hw29.exceptions.EmployeeNotFoundException;
import pro.sky.javacoursepart2.hw29.exceptions.EmployeeStorageIsFullException;
import pro.sky.javacoursepart2.hw29.model.Employee;

import java.util.*;

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
        String key = generateEmployeesMapKey(lastName, firstName, middleName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException("В базе данных уже содержится данный сотрудник.");
        }
        if (employees.size() == maxEmployees) {
            throw new EmployeeStorageIsFullException("База данных переполнена.");
        }
        Employee e = new Employee(firstName, middleName, lastName, department, salary);
        employees.put(key, e);
        return e;
    }

    @Override
    public Employee removeEmployee(String firstName, String middleName, String lastName) {
        String key = generateEmployeesMapKey(lastName, firstName, middleName);
        if (employees.containsKey(key)) {
            return employees.remove(key);
        }
        throw new EmployeeNotFoundException("Указанный сотрудник отсутвует в базе данных.");
    }

    @Override
    public Employee findEmployee(String firstName, String middleName, String lastName) {
        String key = generateEmployeesMapKey(lastName, firstName, middleName);
        if (employees.containsKey(key)) {
            return employees.get(key);
        }
        throw new EmployeeNotFoundException("Указанный сотрудник отсутвует в базе данных.");
    }

    @Override
    public List<Employee> findAllEmployeesSortedByDepartment() {
        return employees.values().stream()
                .sorted(Comparator.comparingInt(employee -> employee.getDepartment()))
                .toList();
    }

//    Map<Integer, String> hashmap = new HashMap<>();
//hashmap.put(1, "Value1");
//hashmap.put(2, "Value2");
//hashmap.put(3, "Value3");
//
//    List<Integer> keysList = Arrays.asList(1, 2);
//
//    Map<Integer, String> subHashmap = hashmap.entrySet()
//            .stream()
//            .filter(x -> keysList.contains(x.getKey()))
//            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//System.out.println(subHashmap);


//    @Override
//    public List<Employee> findEmployeesByDepartment(int department) {
//        return employees.values().stream()
//                .filter(e -> e.getDepartment() == department)
//                .toList();
//    }

    @Override
    public List<Employee> findEmployeesByDepartment(int department) {
        return employees.values().stream()
                .filter(e -> e.getDepartment() == department)
                .toList();
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
// Переделать чтобы возвращало ОТДЕЛ
    @Override
    public Map<String, Employee> increaseSalary(int department, double percent) {
        employees.values().stream()
                .filter(e -> e.getDepartment() == department)
                .forEach(e -> e.setSalary(Math.round(e.getSalary() * (100 + percent)) / 100.0));
        return employees;
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

}
