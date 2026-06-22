package com.gft.calculator.service.operation;

import com.gft.calculator.domain.Operation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class AddOperationHandlerTest {

    private final AddOperationHandler handler = new AddOperationHandler();

    @Test
    void shouldSupportAddOperation() {
        assertThat(handler.supportedOperation()).isEqualTo(Operation.ADD);
    }

    @Test
    void shouldAddOperands() {
        BigDecimal result = handler.calculate(BigDecimal.valueOf(2), BigDecimal.valueOf(3));

        assertThat(result).isEqualByComparingTo(BigDecimal.valueOf(5));
    }
}
