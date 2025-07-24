package sh.roadmap.ecommerce.core.exception;

import lombok.Getter;
import lombok.Setter;
import sh.roadmap.ecommerce.core.utils.MessageUtil;

/**
 * Custom exception handler for the application.
 * Extends RuntimeException to provide additional functionality for handling exceptions
 * with custom error codes and messages.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Getter: Automatically generates getter methods for all fields.</li>
 *   <li>@Setter: Automatically generates setter methods for all fields.</li>
 * </ul>
 */
@Getter
@Setter
public class CustomExceptionHandler extends RuntimeException {

    /**
     * The error code associated with the exception.
     */
    private int code;

    /**
     * The error message providing details about the exception.
     */
    private String message;

    /**
     * Constructs a new CustomExceptionHandler with the specified message.
     *
     * @param message The error message for the exception.
     */
    public CustomExceptionHandler(String message) {
        super(message);
    }

    /**
     * Constructs a new CustomExceptionHandler with the specified error code.
     * The error message is retrieved using the MessageUtil class.
     *
     * @param code The error code for the exception.
     */
    public CustomExceptionHandler(int code) {
        this.code = code;
        this.message = MessageUtil.getMessage(code);
    }

    /**
     * Constructs a new CustomExceptionHandler with the specified error code and parameters.
     * The error message is retrieved using the MessageUtil class with the provided parameters.
     *
     * @param code   The error code for the exception.
     * @param params The parameters to format the error message.
     */
    public CustomExceptionHandler(int code, String... params) {
        this.code = code;
        this.message = MessageUtil.getMessage(code, params);
    }

    /**
     * Constructs a new CustomExceptionHandler with the specified error code and message.
     *
     * @param code    The error code for the exception.
     * @param message The custom error message for the exception.
     */
    public CustomExceptionHandler(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}