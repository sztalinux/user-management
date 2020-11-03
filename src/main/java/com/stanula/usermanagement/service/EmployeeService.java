package com.stanula.usermanagement.service;

import com.stanula.usermanagement.data.Employee;
import com.stanula.usermanagement.repository.EmployeeRepository;
import com.stanula.usermanagement.service.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id: " + id + " not found"));
    }

    public void delete(Integer id) {
        getById(id);
        employeeRepository.deleteById(id);
    }

    public Employee update(Employee updatedEmployee, Integer id) {
        Employee employee = getById(id);
        updatedEmployee.setId(employee.getId());
        return employeeRepository.save(updatedEmployee);
    }

    public Integer create(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee.getId();
    }

    public List<Employee> getAllByAnyParameter(String name, String surname, Integer grade, Integer salary) {
        return employeeRepository.getAllByAnyParameter(name, surname, grade, salary);
    }

}
