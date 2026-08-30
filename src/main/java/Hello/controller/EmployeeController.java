package Hello.controller;

import Hello.models.Employee;
import Hello.service.EmployeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/emp")
public class EmployeeController {


    EmployeeService service;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> getAll() {
        logger.info("Get All Data");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        var emp = service.getEmployeeById(id);
        if(ObjectUtils.isEmpty(emp)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(emp);
    }

    @PostMapping
    public String createEmployee(@Valid @RequestBody Employee emp) {
        return service.createEmployee(emp);
    }

    @PutMapping
    public String updateEmployee(@RequestBody Employee emp) {
        return service.updateEmployee(emp);
    }

    @DeleteMapping("/{id}")
    public String removeEmployeeById(@PathVariable int id) {
        return service.removeEmployeeById(id);
    }

}

