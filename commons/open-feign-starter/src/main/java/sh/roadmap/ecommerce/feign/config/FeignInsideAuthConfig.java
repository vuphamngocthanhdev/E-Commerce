package sh.roadmap.ecommerce.feign.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Feign inside authentication.
 * This class is used to load and manage properties related to Feign inside authentication
 * from the application's configuration files.
 */
@Getter
@Setter
@RefreshScope
@Configuration
@ConfigurationProperties("feign.inside.auth")
public class FeignInsideAuthConfig {

    /**
     * URL prefix for Feign inside requests.
     * This is a constant value used as a base path for Feign endpoints.
     */
    public final static String FEIGN_INSIDE_URL_PREFIX = "/feign";

    /**
     * The authentication key for Feign inside requests.
     * This value is injected from the property `feign.inside.auth.key`.
     */
    @Value("${feign.inside.auth.key}")
    private String key;

    /**
     * The authentication secret for Feign inside requests.
     * This value is injected from the property `feign.inside.auth.secret`.
     */
    @Value("${feign.inside.auth.secret}")
    private String secret;
}