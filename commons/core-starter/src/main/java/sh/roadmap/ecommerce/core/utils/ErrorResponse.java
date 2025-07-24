package sh.roadmap.ecommerce.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Represents an error response object used to encapsulate error details.
 * This class includes error code, an optional field name, and an error message.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Data: Generates boilerplate code such as getters, setters, equals, hashCode, and toString methods.</li>
 *   <li>@NoArgsConstructor: Generates a no-argument constructor.</li>
 *   <li>@Accessors(chain = true): Enables fluent-style setters for the class.</li>
 *   <li>@JsonInclude(JsonInclude.Include.NON_NULL): Ensures the 'field' property is only included in JSON if it is not null.</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ErrorResponse {

    /**
     * The error code representing the type of error.
     */
    private int code;

    /**
     * The name of the field associated with the error, if applicable.
     * This property is only included in JSON if it is not null.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;

    /**
     * The error message providing details about the error.
     */
    private String message;

    /**
     * Constructs an ErrorResponse with the specified error code.
     * The error message is retrieved using the MessageUtil class.
     *
     * @param code The error code representing the type of error.
     */
    public ErrorResponse(int code) {
        this.code = code;
        this.message = MessageUtil.getMessage(code);
    }

    /**
     * Constructs an ErrorResponse with the specified error code and field name.
     * The error message is retrieved using the MessageUtil class.
     *
     * @param code  The error code representing the type of error.
     * @param field The name of the field associated with the error.
     */
    public ErrorResponse(int code, String field) {
        this.code = code;
        this.field = field;
        this.message = MessageUtil.getMessage(code, field);
    }
}
