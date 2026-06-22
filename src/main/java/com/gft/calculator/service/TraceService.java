package com.gft.calculator.service;

import io.corp.calculator.TracerAPI;
import org.springframework.stereotype.Service;

@Service
public class TraceService {

    private final TracerAPI tracer;

    public TraceService(TracerAPI tracer) {
        this.tracer = tracer;
    }

    public <T> void trace(T result) {
        tracer.trace(result);
    }
}
