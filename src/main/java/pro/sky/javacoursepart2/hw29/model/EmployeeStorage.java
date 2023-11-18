package pro.sky.javacoursepart2.hw29.model;

import java.util.HashMap;
import java.util.Map;

public class EmployeeStorage {
    public static final int maxEmployees = 10;
    public static Map<String, Employee> employees = new HashMap<>();

    public static String generateEmployeesMapKey(String lastName, String firstName, String middleName) {
        return lastName + firstName + middleName;
    }

}
