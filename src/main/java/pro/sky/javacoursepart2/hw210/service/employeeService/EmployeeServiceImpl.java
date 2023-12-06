package pro.sky.javacoursepart2.hw210.service.employeeService;

import org.springframework.stereotype.Service;
import pro.sky.javacoursepart2.hw210.exceptions.EmployeeAlreadyAddedException;
import pro.sky.javacoursepart2.hw210.exceptions.EmployeeNotFoundException;
import pro.sky.javacoursepart2.hw210.exceptions.EmployeeStorageIsFullException;
import pro.sky.javacoursepart2.hw210.model.Employee;
import pro.sky.javacoursepart2.hw210.model.EmployeeStorage;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final int maxEmployees = EmployeeStorage.maxEmployees;

    private final Map<String, Employee> employees = EmployeeStorage.employees;

    private String generateEmployeesMapKey(String lastName, String firstName, String middleName) {
        return EmployeeStorage.generateEmployeesMapKey(lastName, firstName, middleName);
    }

    @Override
    public Map<String, Employee> getEmployees() {
        return Collections.unmodifiableMap(employees);
    }

    @Override
    public Employee addEmployee(String firstNameInput, String middleNameInput, String lastNameInput, int department, double salary) {
        String firstName = EmployeeStorage.checkAndCapitalizeName(firstNameInput);
        String middleName = EmployeeStorage.checkAndCapitalizeName(middleNameInput);
        String lastName = EmployeeStorage.checkAndCapitalizeName(lastNameInput);
        if (employees.size() == maxEmployees) {
            throw new EmployeeStorageIsFullException("База данных переполнена.");
        }
        String key = generateEmployeesMapKey(lastName, firstName, middleName);
        employees.entrySet().stream()
                .filter(e -> e.getKey().equals(key))
                .findAny()
                .map(e -> {
                    throw new EmployeeAlreadyAddedException("В базе данных уже содержится сотрудник "
                            + firstName + " " + middleName + " " + lastName + ".");
                })
                .orElseGet(() -> {
                    Employee e = new Employee(firstName, middleName, lastName, department, salary);
                    employees.put(key, e);
                    return e;
                });
        return employees.get(key);

//        //              OLD METHOD
//        if (employees.size() == maxEmployees) {
//            throw new EmployeeStorageIsFullException("База данных переполнена.");
//        }
//        String key = generateEmployeesMapKey(lastName, firstName, middleName);
//        if (employees.containsKey(key)) {
//            throw new EmployeeAlreadyAddedException("В базе данных уже содержится сотрудник "
//                    + firstName + " " + middleName + " " + lastName + ".");
//        }
//        Employee e = new Employee(firstName, middleName, lastName, department, salary);
//        employees.put(key, e);
//        return e;
    }

    @Override
    public Employee removeEmployee(String firstNameInput, String middleNameInput, String lastNameInput) {
        String firstName = EmployeeStorage.checkAndCapitalizeName(firstNameInput);
        String middleName = EmployeeStorage.checkAndCapitalizeName(middleNameInput);
        String lastName = EmployeeStorage.checkAndCapitalizeName(lastNameInput);
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
    public Employee findEmployee(String firstNameInput, String middleNameInput, String lastNameInput) {
        String firstName = EmployeeStorage.checkAndCapitalizeName(firstNameInput);
        String middleName = EmployeeStorage.checkAndCapitalizeName(middleNameInput);
        String lastName = EmployeeStorage.checkAndCapitalizeName(lastNameInput);
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
