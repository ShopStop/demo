package Hello;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    static Map<Integer, Employee> employees = new HashMap<>();

    @GetMapping
    public List<Employee> getAll() {
        return employees.values().stream().toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        var emp = employees.get(id);
        if(ObjectUtils.isEmpty(emp)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(emp);
    }

    @PostMapping
    public String createEmployee(@RequestBody Employee emp) {
        if (employees.containsKey(emp.getId())) {
            return "Employee already present for id:" + emp.getId();
        }
        employees.put(emp.getId(), emp);
        return "Employee added successfully";
    }

    @PutMapping
    public String updateEmployee(@RequestBody Employee emp) {
        if (!employees.containsKey(emp.getId())) {
            return "Emp not present for id: "+ emp.getId() + " cannot update";
        }
        var oldEmp = employees.get(emp.getId());
        if (emp.getDept() != null) oldEmp.setDept(emp.getDept());
        if (emp.getName() != null) oldEmp.setName(emp.getName());
        if (emp.getEmail() != null) oldEmp.setEmail(emp.getEmail());
        employees.put(emp.getId(), oldEmp);
        return "Updated EMP with id " + emp.getId() ;
    }

    @DeleteMapping("/{id}")
    public String removeEmployeeById(@PathVariable int id) {
        var emp = employees.get(id);
        if(ObjectUtils.isEmpty(emp)) {
            return "Emp not present for id: "+ id + " cannot delete";
        }
        employees.remove(id);
        return "Employee removed with id: " + id;
    }




    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class Employee {
        int id;
        String name;
        String email;
        String dept;

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Employee employee = (Employee) o;
            return Objects.equals(id, employee.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }
}

