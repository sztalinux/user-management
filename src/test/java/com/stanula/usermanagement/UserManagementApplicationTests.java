package com.stanula.usermanagement;

import com.stanula.usermanagement.data.Employee;
import com.stanula.usermanagement.repository.EmployeeRepository;
import com.stanula.usermanagement.service.EmployeeService;
import com.stanula.usermanagement.service.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserManagementApplicationTests {

	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private EmployeeRepository employeeRepository;

	private static Employee redEmployee;
	private static final Integer ID = 1;
	private static final String EMPLOYEE_NOT_FOUND_EXCEPTION_TEXT = "Employee with id: " + ID + " not found";

	@BeforeAll
	static void setUp() {
		redEmployee = new Employee(ID, "Sam", "Smith", 1, 1488);
	}

	@Test
	void whenCreateMethodIsCalled_thenRepositorySaveMethodIsInvokedOnce() {
		when(employeeRepository.save(any(Employee.class))).thenReturn(redEmployee);

		employeeService.create(redEmployee);

		verify(employeeRepository, times(1)).save(redEmployee);
	}

	@Test
	void whenUpdateMethodIsCalled_thenRepositoryFindByIdAndSaveMethodsAreInvokedOnce() {
		when(employeeRepository.findById(ID)).thenReturn(Optional.of(new Employee()));

		employeeService.update(redEmployee, ID);

		verify(employeeRepository, times(1)).findById(ID);
		verify(employeeRepository, times(1)).save(redEmployee);
	}

	@Test
	void whenDeleteMethodIsCalled_thenRepositoryFindByIdAndDeleteByIdMethodsAreInvokedOnce() {
		when(employeeRepository.findById(ID)).thenReturn(Optional.of(new Employee()));

		employeeService.delete(ID);

		verify(employeeRepository, times(1)).findById(ID);
		verify(employeeRepository, times(1)).deleteById(ID);
	}

	@Test
	void whenGetByIdMethodIsCalled_thenRepositoryFindByIdAMethodsIsInvokedOnce() {
		when(employeeRepository.findById(ID)).thenReturn(Optional.of(new Employee()));

		employeeService.getById(ID);

		verify(employeeRepository, times(1)).findById(ID);
	}

	@Test
	void whenGetByIdMethodIsCalled_thenEmployeeNotFoundExceptionIsThrown() {
		when(employeeRepository.findById(ID)).thenReturn(Optional.empty());

		assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(
				() -> employeeService.getById(ID)).withMessage(EMPLOYEE_NOT_FOUND_EXCEPTION_TEXT);
	}

	@Test
	void whenGetAllByAnyParametersMethodIsCalled_thenRepositoryFindByIdAMethodsIsInvokedOnce() {
		employeeService.getAllByAnyParameter("Sam", "Smith", 1, 2137);

		verify(employeeRepository, times(1)).getAllByAnyParameter("Sam", "Smith", 1, 2137);
	}

}
