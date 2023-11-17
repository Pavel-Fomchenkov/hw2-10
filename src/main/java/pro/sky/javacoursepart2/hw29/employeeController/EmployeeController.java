package pro.sky.javacoursepart2.hw29.employeeController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.javacoursepart2.hw29.employeeService.EmployeeService;
import pro.sky.javacoursepart2.hw29.model.Employee;

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


    @GetMapping("/edit")
    public Employee editEmployee(@RequestParam(value = "firstName") String firstName,
                                 @RequestParam(value = "middleName") String middleName,
                                 @RequestParam(value = "lastName") String lastName,
                                 @RequestParam(value = "departmentId") int department,
                                 @RequestParam(value = "salary") double salary) {
        return employees.editEmployee(firstName, middleName, lastName, department, salary);
    }
    @GetMapping("/names")
    public String printNames() {
        return employees.printNames();
    }

}
