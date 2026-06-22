package com.gft.calculator.service;

import com.gft.calculator.domain.Operation;
import com.gft.calculator.dto.CalculationRequest;
import com.gft.calculator.dto.CalculationResponse;
import com.gft.calculator.exception.UnsupportedCalculatorOperationException;
import com.gft.calculator.service.operation.OperationHandler;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculatorService {

    private final TraceService traceService;
    private final Map<Operation, OperationHandler> operationHandlers;

    public CalculatorService(TraceService traceService, List<OperationHandler> operationHandlers) {
        this.traceService = traceService;
        this.operationHandlers = buildOperationHandlers(operationHandlers);
    }

    public CalculationResponse calculate(CalculationRequest request) {
        OperationHandler operationHandler = operationHandlers.get(request.operation());

        if (operationHandler == null) {
            throw new UnsupportedCalculatorOperationException(request.operation());
        }

        BigDecimal result = operationHandler.calculate(request.leftOperand(), request.rightOperand());

        traceService.trace(result);

        return new CalculationResponse(
                request.operation(),
                request.leftOperand(),
                request.rightOperand(),
                result
        );
    }

    private Map<Operation, OperationHandler> buildOperationHandlers(List<OperationHandler> operationHandlers) {
        Map<Operation, OperationHandler> handlers = new EnumMap<>(Operation.class);

        operationHandlers.forEach(operationHandler -> {
            Operation supportedOperation = operationHandler.supportedOperation();

            if (handlers.containsKey(supportedOperation)) {
                throw new IllegalStateException("Duplicated handler for operation: " + supportedOperation);
            }

            handlers.put(supportedOperation, operationHandler);
        });

        Arrays.stream(Operation.values())
                .filter(operation -> !handlers.containsKey(operation))
                .findFirst()
                .ifPresent(operation -> {
                    throw new IllegalStateException("Missing handler for operation: " + operation);
                });

        return handlers;
    }
}
