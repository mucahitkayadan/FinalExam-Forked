package edu.miu.cse.vsms.repository;

import edu.miu.cse.vsms.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // We need to find by employee ID
    Optional<Employee> findByEmployeeId(Long employeeId);
    Optional<Employee> findByName(String name);
}
