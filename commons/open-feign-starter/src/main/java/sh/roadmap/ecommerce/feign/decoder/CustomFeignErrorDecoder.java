package sh.roadmap.ecommerce.feign.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import sh.roadmap.ecommerce.core.exception.CustomExceptionHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Custom implementation of Feign's ErrorDecoder to handle HTTP error responses.
 * This class maps specific HTTP status codes to custom exceptions or generic exceptions.
 */
public class CustomFeignErrorDecoder implements ErrorDecoder {

    // Logger instance for logging error details
    private final static Logger logger = LoggerFactory.getLogger(CustomFeignErrorDecoder.class.getName());

    /**
     * Decodes an HTTP response and maps it to an appropriate exception.
     *
     * @param methodKey the Feign client method key
     * @param response  the HTTP response received from the server
     * @return an Exception corresponding to the HTTP status code
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        String requestUrl = response.request().url(); // Extract the request URL
        HttpStatus status = HttpStatus.valueOf(response.status()); // Map the HTTP status code
        String responseBody = extractResponseBody(response); // Extract the response body
        logger.error("Error response: {} {} - Status: {}, Body: {}",
                response.request().httpMethod(),
                requestUrl,
                status,
                responseBody);

        // Map HTTP status codes to specific exceptions
        return switch (status) {
            case BAD_REQUEST -> new CustomExceptionHandler(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Bad request: " + responseBody + requestUrl
            );
            case UNAUTHORIZED -> new CustomExceptionHandler(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Unauthorized access: " + responseBody + requestUrl
            );
            case FORBIDDEN -> new CustomExceptionHandler(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Access denied: " + responseBody + requestUrl
            );
            case NOT_FOUND -> new CustomExceptionHandler(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Resource not found: " + responseBody + requestUrl
            );
            case INTERNAL_SERVER_ERROR -> new RuntimeException(
                    "Internal server error: " + responseBody + requestUrl
            );
            case SERVICE_UNAVAILABLE -> new Exception("Service unavailable: " + responseBody + requestUrl);
            default -> new Exception("Unexpected error: " + responseBody + requestUrl);
        };
    }

    /**
     * Extracts the response body from the Feign Response object.
     *
     * @param response the HTTP response received from the server
     * @return the response body as a String, or a placeholder if extraction fails
     */
    private String extractResponseBody(Response response) {
        try (InputStream bodyIs = response.body().asInputStream()) {
            byte[] bodyBytes = bodyIs.readAllBytes(); // Read the response body as bytes
            return new String(bodyBytes, StandardCharsets.UTF_8); // Convert bytes to a UTF-8 String
        } catch (IOException e) {
            logger.error("Error extracting response body", e); // Log the error
            return "[Could not extract response body]"; // Return a placeholder message
        }
    }
}