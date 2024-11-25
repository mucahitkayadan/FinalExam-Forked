package edu.miu.cse.vsms.exception;

import edu.miu.cse.vsms.exception.employee.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
        Map<String, String> response = new HashMap<String, String>();
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); //return 404
    }

}
