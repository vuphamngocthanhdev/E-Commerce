package sh.roadmap.ecommerce.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sh.roadmap.ecommerce.feign.config.FeignInsideAuthConfig;

import java.util.Set;

/**
 * Feign interceptor for adding authentication headers to requests.
 * Handles both internal service calls and external calls with secure header management.
 */
@Component
@ConditionalOnClass({RequestInterceptor.class})
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FeignBasicAuthRequestInterceptor.class);
    private static final Set<String> ALLOWED_AUTH_PREFIXES = Set.of("Bearer ", "Basic ");

    private final FeignInsideAuthConfig feignInsideAuthConfig;

    /**
     * Constructor for FeignBasicAuthRequestInterceptor.
     *
     * @param feignInsideAuthConfig the configuration for Feign inside authentication
     */
    public FeignBasicAuthRequestInterceptor(FeignInsideAuthConfig feignInsideAuthConfig) {
        this.feignInsideAuthConfig = feignInsideAuthConfig;
    }

    /**
     * Applies authentication headers to the Feign request template with security validation.
     * For internal service calls, uses configured authentication key and secret.
     * For external calls, validates and propagates original authorization header if allowed.
     *
     * @param requestTemplate the Feign request template to modify
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();
        final String requestUrl = request.getRequestURI();

        if (requestUrl.startsWith(FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX)) {
            // Internal service call - use configured authentication
            if (feignInsideAuthConfig.getKey() != null && feignInsideAuthConfig.getSecret() != null) {
                requestTemplate.header(feignInsideAuthConfig.getKey(), feignInsideAuthConfig.getSecret());
            } else {
                logger.warn("Internal authentication attempted but key or secret is null");
            }
        } else {
            // External call - validate and propagate authorization header if allowed
            final String authorization = request.getHeader("Authorization");
            if (isValidAuthorizationHeader(authorization)) {
                requestTemplate.header("Authorization", authorization);
            }
        }
    }

    /**
     * Validates that the authorization header has an allowed format.
     * Helps prevent header injection attacks.
     *
     * @param authHeader the authorization header value to validate
     * @return true if the header has a valid format, false otherwise
     */
    private boolean isValidAuthorizationHeader(String authHeader) {
        if (authHeader == null || authHeader.trim().isEmpty()) {
            return false;
        }

        return ALLOWED_AUTH_PREFIXES.stream()
                .anyMatch(authHeader::startsWith);
    }
}
