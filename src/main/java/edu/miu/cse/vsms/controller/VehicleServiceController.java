package edu.miu.cse.vsms.controller;

import edu.miu.cse.vsms.dto.request.ServiceRequestDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.exception.DataIntegrityViolationException;
import edu.miu.cse.vsms.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class VehicleServiceController {
    
    private final VehicleService vehicleService;

    // Assign a service to an employee
    @PostMapping
    public ResponseEntity<VehicleServiceResponseDto> assignService(@RequestBody ServiceRequestDto serviceRequestDto) {
        // The tricky part is that we also need to get the employee here.
        // Employee has to be found rom the id first, but it is done in the service already.

        // There is another important thing is that both serviceRequestDto and response DTOs are the same. Which means that no need for mapping.

        try {
            return ResponseEntity.ok(vehicleService.assignService(serviceRequestDto));
        } catch (Exception e) {
            throw new DataIntegrityViolationException("There was an error while assigning the service " + e.getMessage());
        }


    }
    
}
