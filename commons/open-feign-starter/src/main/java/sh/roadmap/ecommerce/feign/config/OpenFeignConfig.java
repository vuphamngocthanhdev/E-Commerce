package sh.roadmap.ecommerce.feign.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.roadmap.ecommerce.feign.decoder.CustomFeignErrorDecoder;

/**
 * Configuration class for customizing Feign client behavior.
 * This class defines beans to configure Feign-specific components.
 */
@Configuration
public class OpenFeignConfig {

    /**
     * Creates a custom Feign ErrorDecoder bean.
     * The ErrorDecoder is responsible for handling errors returned by Feign clients.
     *
     * @return an instance of CustomFeignErrorDecoder
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}