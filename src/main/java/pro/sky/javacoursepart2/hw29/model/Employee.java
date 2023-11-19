package pro.sky.javacoursepart2.hw29.model;

import java.util.Objects;

public class Employee {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private static int counter;
    private final int id;
    private int department;
    private double salary;

    public Employee(String firstName, String middleName, String lastName, int department, double salary) {
        id = ++counter;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.department = department;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("id: %d - %s %s %s, отдел %d, оклад: %.2f",
                id, lastName, firstName, middleName, department, salary);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(lastName, employee.lastName)
                && Objects.equals(firstName, employee.firstName)
                && Objects.equals(middleName, employee.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName);
    }

    // Spring needs getters to serialize object's private properties when it outputs them to web page
    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public int getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
