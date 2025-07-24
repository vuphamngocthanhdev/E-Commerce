package sh.roadmap.ecommerce.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Generic API response class for encapsulating HTTP response details.
 * Provides methods to create success and error responses with optional data and error details.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Data: Generates boilerplate code such as getters, setters, equals, hashCode, and toString methods.</li>
 *   <li>@NoArgsConstructor: Generates a no-argument constructor.</li>
 *   <li>@Accessors(chain = true): Enables fluent-style setters for the class.</li>
 *   <li>@JsonInclude(JsonInclude.Include.NON_NULL): Ensures that null fields are excluded from JSON serialization.</li>
 * </ul>
 *
 * @param <T> The type of the data payload in the response.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ApiResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -3_856_239_863_444_862_004L;

    /**
     * HTTP status code for successful responses.
     */
    private static final int SUCCESS = HttpStatus.OK.value();

    /**
     * The HTTP status code of the response.
     */
    private int code;

    /**
     * The message providing additional details about the response.
     */
    private String message;

    /**
     * The data payload of the response, if applicable.
     * This field is only included in the JSON response if it is not null.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * The list of error details, if applicable.
     * This field is only included in the JSON response if it is not null.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorResponse> errors;

    /**
     * Creates a success response with the provided data.
     *
     * @param data The payload to include in the response.
     * @return An ApiResponse instance with success status and the provided data.
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>()
                .setCode(SUCCESS)
                .setMessage(MessageUtil.getMessage("api.response.success"))
                .setData(data);
    }

    /**
     * Creates a success response without any data.
     *
     * @return An ApiResponse instance with success status.
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<T>()
                .setCode(SUCCESS)
                .setMessage(MessageUtil.getMessage("api.response.success"));
    }

    /**
     * Creates an error response with the specified status code and error details.
     *
     * @param code   The HTTP status code of the error response.
     * @param errors The list of error details to include in the response.
     * @return An ApiResponse instance with error status and the provided details.
     */
    public static <T> ApiResponse<T> error(int code, List<ErrorResponse> errors) {
        return new ApiResponse<T>()
                .setCode(code)
                .setMessage(MessageUtil.getMessage(code))
                .setErrors(errors);
    }

    /**
     * Creates an error response with a custom status code, message, and error details.
     *
     * @param code    The HTTP status code of the error response.
     * @param message The custom message key for the error response.
     * @param errors  The list of error details to include in the response.
     * @return An ApiResponse instance with error status and the provided details.
     */
    public static <T> ApiResponse<T> error(int code, String message, List<ErrorResponse> errors) {
        return new ApiResponse<T>()
                .setCode(code)
                .setMessage(MessageUtil.getMessage(message))
                .setErrors(errors);
    }

    /**
     * Creates a bad request error response with the specified error details.
     *
     * @param errors The list of error details to include in the response.
     * @return An ApiResponse instance with bad request status.
     */
    public static <T> ApiResponse<T> badRequest(List<ErrorResponse> errors) {
        return error(ErrorCode.BAD_REQUEST, errors);
    }
}