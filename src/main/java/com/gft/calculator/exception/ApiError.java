package com.gft.calculator.exception;

import java.util.List;

public record ApiError(
        String code,
        String message,
        List<String> details
) {

    public static ApiError of(String code, String message, List<String> details) {
        return new ApiError(code, message, details);
    }
}
