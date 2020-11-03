package com.stanula.usermanagement.controller;

import com.stanula.usermanagement.data.Employee;
import com.stanula.usermanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Integer id) {
        Employee employee = employeeService.getById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@RequestBody @Valid Employee employee, @PathVariable Integer id) {
        employee = employeeService.update(employee, id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Integer> create(@RequestBody @Valid Employee employee) {
        Integer id = employeeService.create(employee);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllByAnyParameter(@RequestParam(required = false) String name,
                                                               @RequestParam(required = false) String surname,
                                                               @RequestParam(required = false) Integer grade,
                                                               @RequestParam(required = false) Integer salary) {
        List<Employee> employees = employeeService.getAllByAnyParameter(name, surname, grade, salary);
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
    }
}
