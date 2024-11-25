package edu.miu.cse.vsms.service.impl;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.EmployeeRepository;
import edu.miu.cse.vsms.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseDto addEmployee(EmployeeRequestDto employeeRequestDto) {
        Employee newEmployee = new Employee();
        newEmployee.setName(employeeRequestDto.name());
        newEmployee.setEmail(employeeRequestDto.email());
        newEmployee.setPhone(employeeRequestDto.phone());
        newEmployee.setHireDate(employeeRequestDto.hireDate());

        VService vService = new VService();
        vService.setEmployee(newEmployee);
        employeeRepository.save(newEmployee);

        return mapToResponseDto(newEmployee);
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDto> employeeResponseDtos = new ArrayList<>();
        for (Employee employee : employees) {
            employeeResponseDtos.add(mapToResponseDto(employee));
        }

        return employeeResponseDtos;
    }

    @Override
    public Optional<EmployeeResponseDto> getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(this::mapToResponseDto);
    }

    @Override
    public EmployeeResponseDto partiallyUpdateEmployee(Long id, Map<String, Object> updates) {

        // Fetch the employee by ID or throw an exception if not found
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));

        // Apply each update based on the key
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    Field field = ReflectionUtils.findField(Employee.class, key);
                    if (field != null) {
                        ReflectionUtils.makeAccessible(field);
                        ReflectionUtils.setField(field, employee, ((Employee) value).getName());
                    }

                    break;
                case "email":
                    Field field1 = ReflectionUtils.findField(Employee.class, key);
                    if (field1 != null) {
                        ReflectionUtils.makeAccessible(field1);
                        ReflectionUtils.setField(field1, employee, ((Employee) value).getEmail());
                    }

                    break;
                case "phone":
                    Field field2 = ReflectionUtils.findField(Employee.class, key);
                    if (field2 != null) {
                        ReflectionUtils.makeAccessible(field2);
                        ReflectionUtils.setField(field2, employee, ((Employee) value).getPhone());
                    }

                    break;
                case "hireDate":
                    Field field3 = ReflectionUtils.findField(Employee.class, key);
                    if (field3 != null) {
                        ReflectionUtils.makeAccessible(field3);
                        ReflectionUtils.setField(field3, employee, ((Employee) value).getHireDate());
                    }

                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToResponseDto(updatedEmployee);
    }

    private EmployeeResponseDto mapToResponseDto(Employee employee) {
        List<VehicleServiceResponseDto> serviceDtos = employee.getVServices().stream()
                .map(service -> new VehicleServiceResponseDto(
                        service.getVServiceId(),
                        service.getServiceName(),
                        service.getCost(),
                        service.getVehicleType()
                )).toList();

        return new EmployeeResponseDto(
                employee.getEmployeeId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getHireDate(),
                serviceDtos
        );
    }
}
