package com.gft.calculator.exception;

import com.gft.calculator.domain.Operation;

public class UnsupportedCalculatorOperationException extends RuntimeException {

    public UnsupportedCalculatorOperationException(Operation operation) {
        super("Unsupported operation: " + operation);
    }
}
