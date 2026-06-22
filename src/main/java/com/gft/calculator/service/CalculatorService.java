package com.gft.calculator.service;

import com.gft.calculator.dto.CalculationRequest;
import com.gft.calculator.dto.CalculationResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculatorService {

    private final TraceService traceService;

    public CalculatorService(TraceService traceService) {
        this.traceService = traceService;
    }

    public CalculationResponse calculate(CalculationRequest request) {
        BigDecimal result = switch (request.operation()) {
            case ADD -> request.leftOperand().add(request.rightOperand());
            case SUBTRACT -> request.leftOperand().subtract(request.rightOperand());
        };

        traceService.trace(result);

        return new CalculationResponse(
                request.operation(),
                request.leftOperand(),
                request.rightOperand(),
                result
        );
    }
}
