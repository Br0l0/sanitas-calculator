package com.gft.calculator.service.operation;

import com.gft.calculator.domain.Operation;

import java.math.BigDecimal;

public interface OperationHandler {

    Operation supportedOperation();

    BigDecimal calculate(BigDecimal leftOperand, BigDecimal rightOperand);
}
