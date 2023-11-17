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
@RequestMapping("/departments")
public class DepartmentsController {
    private final EmployeeService employees;

    private DepartmentsController(EmployeeService employees) {
        this.employees = employees;
    }

    @GetMapping("/all")
    public List<Employee> findAllEmployeesSortedByDepartment() {
        return employees.findAllEmployeesSortedByDepartment();
    }

    @GetMapping(value = "/all", params = "departmentId")
    public List<Employee> findEmployeesByDepartment(@RequestParam(value = "departmentId") int department) {
        return employees.findEmployeesByDepartment(department);
    }

    @GetMapping(value = "/lower-salary", params = "salary")
    public Map<String, Employee> employeesWithSalaryLowerOrEqualThen(@RequestParam(value = "salary") double salaryLimit) {
        return employees.employeesWithSalaryLowerOrEqualThen(salaryLimit);
    }

    @GetMapping(value = "/higher-salary", params = "salary")
    public Map<String, Employee> employeesWithSalaryHigherOrEqualThen(@RequestParam(value = "salary") int salaryLimit) {
        return employees.employeesWithSalaryHigherOrEqualThen(salaryLimit);
    }

    @GetMapping(value = "/min-salary")
    public Employee minSalaryEmployee() {
        return employees.minSalaryEmployee();
    }

    @GetMapping(value = "/min-salary", params = "departmentId")
    public Employee minSalaryEmployee(@RequestParam(value = "departmentId") int department) {
        return employees.minSalaryEmployee(department);
    }

    @GetMapping(value = "/max-salary")
    public Employee maxSalaryEmployee() {
        return employees.maxSalaryEmployee();
    }

    @GetMapping(value = "/max-salary", params = "departmentId")
    public Employee maxSalaryEmployee(@RequestParam(value = "departmentId") int department) {
        return employees.maxSalaryEmployee(department);
    }

    @GetMapping("/total-salary")
    public double expencesSalary() {
        return employees.expencesSalary();
    }

    @GetMapping(value = "/total-salary", params = "departmentId")
    public double expencesSalary(@RequestParam(value = "departmentId") int department) {
        return employees.expencesSalary(department);
    }

    @GetMapping("/average-salary")
    public double averageSalary() {
        return employees.averageSalary();
    }

    @GetMapping(value = "/average-salary", params = "departmentId")
    public double averageSalary(@RequestParam(value = "departmentId") int department) {
        return employees.averageSalary(department);
    }

    @GetMapping(value = "/increase-salary", params = "percent")
    public Map<String, Employee> increaseSalary(@RequestParam(value = "percent") double percent) {
        return employees.increaseSalary(percent);
    }

    @GetMapping(value = "/increase-salary", params = {"departmentId", "percent"})
    public Map<String, Employee> increaseSalary(@RequestParam(value = "departmentId") int department,
                                                @RequestParam(value = "percent") double percent) {
        return employees.increaseSalary(department, percent);
    }

    @GetMapping(value = "/increase-salary", params = {"firstName", "middleName", "lastName", "percent"})
    public Employee increaseSalary(@RequestParam(value = "firstName") String firstName,
                                   @RequestParam(value = "middleName") String middleName,
                                   @RequestParam(value = "lastName") String lastName,
                                   @RequestParam(value = "percent") double percent) {
        return employees.increaseSalary(firstName, middleName, lastName, percent);
    }
}
