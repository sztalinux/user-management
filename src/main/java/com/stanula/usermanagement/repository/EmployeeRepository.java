package com.stanula.usermanagement.repository;

import com.stanula.usermanagement.data.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FROM Employee e WHERE " +
            "(:name is null or lower(e.name) = lower(:name)) " +
            "and (:surname is null or lower(e.surname) = lower(:surname)) " +
            "and (:grade is null or e.grade = :grade) " +
            "and (:salary is null or e.salary = :salary)")
    List<Employee> getAllByAnyParameter(@Param("name") String name, @Param("surname") String surname,
                                        @Param("grade") Integer grade, @Param("salary") Integer salary);

}
