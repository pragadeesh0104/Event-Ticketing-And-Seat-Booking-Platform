package com.eventbooking.dto.response;

import java.util.List;

/**
 * Standard API Response Envelope.
 *
 * @param <T> Response data type
 */
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private List<String> errors;

    public ApiResponse() {}

    public ApiResponse(boolean success, T data, String message, List<String> errors) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.errors = errors;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message, null);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, null, message, null);
    }

    public static <T> ApiResponse<T> error(String message, List<String> errors) {
        return new ApiResponse<>(false, null, message, errors);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, null, message, null);
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}
