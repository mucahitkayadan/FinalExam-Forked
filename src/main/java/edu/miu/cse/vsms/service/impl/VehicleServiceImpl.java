package edu.miu.cse.vsms.service.impl;

import edu.miu.cse.vsms.dto.request.ServiceRequestDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.EmployeeRepository;
import edu.miu.cse.vsms.repository.VehicleServiceRepository;
import edu.miu.cse.vsms.service.VehicleService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleServiceRepository vehicleServiceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public VehicleServiceResponseDto assignService(ServiceRequestDto serviceRequestDto) {
        // Since there is no composition in the request dto records, we will use them as separate repos.
        // Note that service request dto has employee id relation, not a composition.
        // Let's assign the 4 parameters of the service request dto
        VService vService = new VService();
        vService.setServiceName(serviceRequestDto.serviceName());
        vService.setVServiceId(serviceRequestDto.employeeId());
        vService.setCost(serviceRequestDto.cost());
        vService.setVehicleType(serviceRequestDto.vehicleType());

        // Now, we need to find the employee by its ID.
        Optional<Employee> employee = employeeRepository.findByEmployeeId(serviceRequestDto.employeeId());
        // We got the employee from the db, now set it to the service
        vService.setEmployee(employee.orElse(null));

        VService savedService = vehicleServiceRepository.save(vService);


        return new VehicleServiceResponseDto(
                savedService.getVServiceId(),
                savedService.getServiceName(),
                savedService.getCost(),
                savedService.getVehicleType());
    }
}
