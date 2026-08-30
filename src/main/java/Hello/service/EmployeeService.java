package Hello.service;

import Hello.exception.EmployeeNotFoundException;
import Hello.models.Employee;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    static Map<Integer, Employee> employees = new HashMap<>();

    public List<Employee> getAll() {
        return employees.values().stream().toList();
    }

    public Employee getEmployeeById(@PathVariable int id) {
        return employees.get(id);
    }

    public String createEmployee(Employee emp) {
        if (employees.containsKey(emp.getId())) {
            throw new EmployeeNotFoundException("Employee already present for id:" + emp.getId());
        }
        employees.put(emp.getId(), emp);
        return "Employee added successfully";
    }

    public String updateEmployee(Employee emp) {
        if (!employees.containsKey(emp.getId())) {
            throw new EmployeeNotFoundException("Emp not present for id: "+ emp.getId() + " cannot update");
        }
        var oldEmp = employees.get(emp.getId());
        if (emp.getDept() != null) oldEmp.setDept(emp.getDept());
        if (emp.getName() != null) oldEmp.setName(emp.getName());
        if (emp.getEmail() != null) oldEmp.setEmail(emp.getEmail());
        employees.put(emp.getId(), oldEmp);
        return "Updated EMP with id " + emp.getId() ;
    }

    public String removeEmployeeById(int id) {
        var emp = employees.get(id);
        if(ObjectUtils.isEmpty(emp)) {
            throw new EmployeeNotFoundException("Emp not present for id: "+ id + " cannot delete");
        }
        employees.remove(id);
        return "Employee removed with id: " + id;
    }
}
