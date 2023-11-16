package pro.sky.javacoursepart2.hw29.employeeController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.javacoursepart2.hw29.employeeService.EmployeeService;
import pro.sky.javacoursepart2.hw29.model.Employee;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employees;

    private EmployeeController(EmployeeService employees) {
        this.employees = employees;
    }

    // CODE FOR AUTOMATING ROUTINE OPERATIONS WHILE TESTING
    @GetMapping("/test")
    public Map<String, Employee> testEmployee() {
        addEmployee("Ivan", "Ivanovich", "Ivanov", 1, 100.0);
        addEmployee("Petr", "Petrovich", "Petrov", 2, 75.0);
        addEmployee("Sidor", "Sidorovich", "Sidorov", 3, 50.0);
        addEmployee("Aleksey", "Alekseevich", "Alekseev", 1, 80.0);
        addEmployee("Vasiliy", "Vasilievich", "Vasilyev", 2, 60.0);
        addEmployee("Pavel", "Pavlovich", "Pavlov", 3, 100.0);
        addEmployee("Andrey", "Andreevich", "Andreev", 1, 100.0);
        addEmployee("Aleksandr", "Aleksandrovich", "Aleksandrov", 2, 100.0);
        addEmployee("Dmitriy", "Dmitrievich", "Dmitriev", 3, 90.0);
        addEmployee("Nikolay", "Nikolaevich", "Nikolaev", 1, 85.5);
        return getEmployees();
    }
    // END OF CODE FOR AUTOMATING ROUTINE OPERATIONS WHILE TESTING

    @GetMapping
    public Map<String, Employee> getEmployees() {
        return employees.getEmployees();
    }

    @GetMapping("/add")
    public Employee addEmployee(@RequestParam(value = "firstName") String firstName,
                                @RequestParam(value = "middleName") String middleName,
                                @RequestParam(value = "lastName") String lastName,
                                @RequestParam(value = "departmentId") int department,
                                @RequestParam(value = "salary") double salary) {
        return employees.addEmployee(firstName, middleName, lastName, department, salary);
    }

    @GetMapping("/remove")
    public Employee removeEmployees(@RequestParam(value = "firstName") String firstName,
                                    @RequestParam(value = "middleName") String middleName,
                                    @RequestParam(value = "lastName") String lastName) {
        return employees.removeEmployee(firstName, middleName, lastName);
    }

    @GetMapping("/find")
    public Employee findEmployee(@RequestParam(value = "firstName") String firstName,
                                 @RequestParam(value = "middleName") String middleName,
                                 @RequestParam(value = "lastName") String lastName) {
        return employees.findEmployee(firstName, middleName, lastName);
    }

    @GetMapping("/departments/all")
    public List<Employee> findAllEmployeesSortedByDepartment() {
        return employees.findAllEmployeesSortedByDepartment();
    }

    @GetMapping(value = "/departments/all", params = "departmentId")
    public List<Employee> findEmployeesByDepartment(@RequestParam(value = "departmentId") int department) {
        return employees.findEmployeesByDepartment(department);
    }

    @GetMapping(value = "/departments/min-salary")
    public Employee minSalaryEmployee() {
        return employees.minSalaryEmployee();
    }

    @GetMapping(value = "/departments/min-salary", params = "departmentId")
    public Employee minSalaryEmployee(@RequestParam(value = "departmentId") int department) {
        return employees.minSalaryEmployee(department);
    }

    @GetMapping(value = "/departments/max-salary")
    public Employee maxSalaryEmployee() {
        return employees.maxSalaryEmployee();
    }

    @GetMapping(value = "/departments/max-salary", params = "departmentId")
    public Employee maxSalaryEmployee(@RequestParam(value = "departmentId") int department) {
        return employees.maxSalaryEmployee(department);
    }

    @GetMapping("/departments/total-salary")
    public double expencesSalary() {
        return employees.expencesSalary();
    }

    @GetMapping(value = "/departments/total-salary", params = "departmentId")
    public double expencesSalary(@RequestParam(value = "departmentId") int department) {
        return employees.expencesSalary(department);
    }

    @GetMapping("/departments/average-salary")
    public double averageSalary() {
        return employees.averageSalary();
    }

    @GetMapping(value = "/departments/average-salary", params = "departmentId")
    public double averageSalary(@RequestParam(value = "departmentId") int department) {
        return employees.averageSalary(department);
    }

    @GetMapping(value = "/departments/increase-salary", params = "percent")
    public Map<String, Employee> increaseSalary(@RequestParam(value = "percent") double percent) {
        return employees.increaseSalary(percent);
    }

    @GetMapping(value = "/departments/increase-salary", params = {"departmentId", "percent"})
    public Map<String, Employee> increaseSalary(@RequestParam(value = "departmentId") int department,
                                                @RequestParam(value = "percent") double percent) {
        return employees.increaseSalary(department, percent);
    }

    @GetMapping(value = "/departments/increase-salary", params = {"firstName", "middleName", "lastName", "percent"})
    public Employee increaseSalary(@RequestParam(value = "firstName") String firstName,
                                   @RequestParam(value = "middleName") String middleName,
                                   @RequestParam(value = "lastName") String lastName,
                                   @RequestParam(value = "percent") double percent) {
        return employees.increaseSalary(firstName, middleName, lastName, percent);
    }

    @GetMapping("/edit")
    public Employee editEmployee(@RequestParam(value = "firstName") String firstName,
                                 @RequestParam(value = "middleName") String middleName,
                                 @RequestParam(value = "lastName") String lastName,
                                 @RequestParam(value = "departmentId") int department,
                                 @RequestParam(value = "salary") double salary) {
        return employees.editEmployee(firstName, middleName, lastName, department, salary);
    }
}
