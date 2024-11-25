package edu.miu.cse.vsms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.exception.DataIntegrityViolationException;
import edu.miu.cse.vsms.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        System.out.println("setUp");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }

    @Test
    void addEmployee() throws Exception {
        System.out.println("addEmployee");

        EmployeeRequestDto employeeRequestDto =
                new EmployeeRequestDto("muja", "mujakayadan@outlook.com", "641 233 9607", LocalDate.of(2024, 5, 12));
        EmployeeResponseDto employeeResponseDto =
                new EmployeeResponseDto(null,"muja", "mujakayadan@outlook.com", "641 233 9607", LocalDate.of(2024, 5, 12), new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/employees")
                .content(asJsonString(new EmployeeRequestDto(
                        "Muja",
                        "mujakayadan@outlook.com",
                        "641 233 9607",
                                LocalDate.now())
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").exists());


    }

    // Helper method for converting to Json String,
    // Defined public so can be used in other tests too.
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new DataIntegrityViolationException("Error with the database");
        }
    }
}