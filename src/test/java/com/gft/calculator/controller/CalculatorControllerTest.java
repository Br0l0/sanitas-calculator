package com.gft.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.calculator.domain.Operation;
import com.gft.calculator.dto.CalculationRequest;
import com.gft.calculator.dto.CalculationResponse;
import com.gft.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CalculatorService calculatorService;

    @Test
    void shouldCalculateOperation() throws Exception {
        CalculationRequest request = new CalculationRequest(
                Operation.ADD,
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(3)
        );
        CalculationResponse response = new CalculationResponse(
                Operation.ADD,
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(5)
        );

        when(calculatorService.calculate(any(CalculationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("ADD"))
                .andExpect(jsonPath("$.leftOperand").value(2))
                .andExpect(jsonPath("$.rightOperand").value(3))
                .andExpect(jsonPath("$.result").value(5));

        verify(calculatorService).calculate(request);
    }

    @Test
    void shouldReturnBadRequestWhenOperationIsMissing() throws Exception {
        String request = """
                {
                  "leftOperand": 2,
                  "rightOperand": 3
                }
                """;

        mockMvc.perform(post("/api/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOperandIsMissing() throws Exception {
        String request = """
                {
                  "operation": "ADD",
                  "leftOperand": 2
                }
                """;

        mockMvc.perform(post("/api/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
}
