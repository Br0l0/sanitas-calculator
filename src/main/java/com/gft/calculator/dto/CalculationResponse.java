package com.gft.calculator.dto;

import com.gft.calculator.domain.Operation;

import java.math.BigDecimal;

public record CalculationResponse(
        Operation operation,
        BigDecimal leftOperand,
        BigDecimal rightOperand,
        BigDecimal result
) {
}
