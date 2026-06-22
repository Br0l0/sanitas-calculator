package com.gft.calculator.service;

import com.gft.calculator.domain.Operation;
import com.gft.calculator.dto.CalculationRequest;
import com.gft.calculator.dto.CalculationResponse;
import com.gft.calculator.service.operation.AddOperationHandler;
import com.gft.calculator.service.operation.OperationHandler;
import com.gft.calculator.service.operation.SubtractOperationHandler;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CalculatorServiceTest {

    private final TraceService traceService = mock(TraceService.class);
    private final CalculatorService calculatorService = new CalculatorService(
            traceService,
            List.of(new AddOperationHandler(), new SubtractOperationHandler())
    );

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

    @Test
    void shouldFailFastWhenOperationHandlerIsMissing() {
        assertThatThrownBy(() -> new CalculatorService(traceService, List.of(new AddOperationHandler())))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Missing handler for operation: SUBTRACT");
    }

    @Test
    void shouldFailFastWhenOperationHandlerIsDuplicated() {
        OperationHandler duplicatedAddHandler = new AddOperationHandler();

        assertThatThrownBy(() -> new CalculatorService(
                traceService,
                List.of(new AddOperationHandler(), duplicatedAddHandler, new SubtractOperationHandler())
        ))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Duplicated handler for operation: ADD");
    }
}
