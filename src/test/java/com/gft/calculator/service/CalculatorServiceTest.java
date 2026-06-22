package com.gft.calculator.service;

import com.gft.calculator.domain.Operation;
import com.gft.calculator.dto.CalculationRequest;
import com.gft.calculator.dto.CalculationResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CalculatorServiceTest {

    private final TraceService traceService = mock(TraceService.class);
    private final CalculatorService calculatorService = new CalculatorService(traceService);

    @Test
    void shouldAddTwoOperandsAndTraceResult() {
        CalculationRequest request = new CalculationRequest(
                Operation.ADD,
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(3)
        );

        CalculationResponse response = calculatorService.calculate(request);

        assertThat(response.result()).isEqualByComparingTo(BigDecimal.valueOf(5));
        assertThat(response.operation()).isEqualTo(Operation.ADD);
        verify(traceService).trace(BigDecimal.valueOf(5));
    }

    @Test
    void shouldSubtractTwoOperandsAndTraceResult() {
        CalculationRequest request = new CalculationRequest(
                Operation.SUBTRACT,
                BigDecimal.valueOf(7),
                BigDecimal.valueOf(4)
        );

        CalculationResponse response = calculatorService.calculate(request);

        assertThat(response.result()).isEqualByComparingTo(BigDecimal.valueOf(3));
        assertThat(response.operation()).isEqualTo(Operation.SUBTRACT);
        verify(traceService).trace(BigDecimal.valueOf(3));
    }
}
