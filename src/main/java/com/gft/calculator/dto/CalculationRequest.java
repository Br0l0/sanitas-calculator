package com.gft.calculator.dto;

import com.gft.calculator.domain.Operation;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CalculationRequest(
        @NotNull Operation operation,
        @NotNull BigDecimal leftOperand,
        @NotNull BigDecimal rightOperand
) {
}
