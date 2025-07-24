package sh.roadmap.ecommerce.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sh.roadmap.ecommerce.core.utils.ApiResponse;
import sh.roadmap.ecommerce.core.utils.ErrorCode;
import sh.roadmap.ecommerce.core.utils.ErrorResponse;
import sh.roadmap.ecommerce.core.utils.MessageUtil;

import java.util.List;

/**
 * Global exception handler for the application.
 * Provides centralized handling of exceptions and returns appropriate API responses.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@RestControllerAdvice: Indicates that this class provides global exception handling for REST controllers.</li>
 *   <li>@ExceptionHandler: Marks methods to handle specific exception types.</li>
 *   <li>@ResponseStatus: Specifies the HTTP status code to return for the handled exceptions.</li>
 * </ul>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles MethodArgumentNotValidException, which occurs when validation on an argument fails.
     * Constructs a list of error details and returns a bad request response.
     *
     * @param ex The MethodArgumentNotValidException instance containing validation errors.
     * @return An ApiResponse object with HTTP 400 status and a list of validation error details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    ErrorResponse errorResponse = new ErrorResponse();
                    errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
                    errorResponse.setMessage(MessageUtil.getMessage(error.getCode()));
                    errorResponse.setField(error.getField());
                    return errorResponse;
                })
                .toList();
        return ApiResponse.badRequest(errors);
    }

    /**
     * Handles general exceptions that are not explicitly handled by other methods.
     * Returns an internal server error response with a generic error message.
     *
     * @param ex The Exception instance representing the error.
     * @return An ApiResponse object with HTTP 500 status and a generic error message.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleGeneralException(Exception ex) {
        return ApiResponse.error(
                ErrorCode.INTERNAL_SERVER_ERROR,
                MessageUtil.getMessage(ErrorCode.INTERNAL_SERVER_ERROR),
                null
        );
    }
}