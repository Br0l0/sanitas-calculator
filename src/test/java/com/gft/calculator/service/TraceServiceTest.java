package com.gft.calculator.service;

import io.corp.calculator.TracerAPI;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TraceServiceTest {

    @Test
    void shouldDelegateResultToTracer() {
        TracerAPI tracer = mock(TracerAPI.class);
        TraceService traceService = new TraceService(tracer);

        traceService.trace(7);

        verify(tracer).trace(7);
    }
}
