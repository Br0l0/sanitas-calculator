package com.gft.calculator.service.operation;

import com.gft.calculator.domain.Operation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SubtractOperationHandler implements OperationHandler {

    @Override
    public Operation supportedOperation() {
        return Operation.SUBTRACT;
    }

    @Override
    public BigDecimal calculate(BigDecimal leftOperand, BigDecimal rightOperand) {
        return leftOperand.subtract(rightOperand);
    }
}
