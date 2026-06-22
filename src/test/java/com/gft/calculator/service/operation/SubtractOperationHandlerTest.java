package com.gft.calculator.service.operation;

import com.gft.calculator.domain.Operation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class SubtractOperationHandlerTest {

    private final SubtractOperationHandler handler = new SubtractOperationHandler();

    @Test
    void shouldSupportSubtractOperation() {
        assertThat(handler.supportedOperation()).isEqualTo(Operation.SUBTRACT);
    }

    @Test
    void shouldSubtractOperands() {
        BigDecimal result = handler.calculate(BigDecimal.valueOf(7), BigDecimal.valueOf(4));

        assertThat(result).isEqualByComparingTo(BigDecimal.valueOf(3));
    }
}
