package pro.sky.javacoursepart2.hw210.model;

import org.apache.commons.lang3.StringUtils;
import pro.sky.javacoursepart2.hw210.exceptions.EmployeeStorageBadRequestException;

import java.util.HashMap;
import java.util.Map;

public class EmployeeStorage {
    public static final int maxEmployees = 10;
    public static Map<String, Employee> employees = new HashMap<>();

    public static String generateEmployeesMapKey(String lastName, String firstName, String middleName) {
        return lastName + firstName + middleName;
    }

    public static String checkAndCapitalizeName(String name) {
        if (StringUtils.isAlpha(name)) {
            return StringUtils.capitalize(name);
        } else
            throw new EmployeeStorageBadRequestException("Имя, отчество или фамилия могут содержать только из буквы.");
    }
}
