package sh.roadmap.ecommerce.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * Configuration class for setting up the message source for internationalization (i18n) support.
 * Defines a bean for ResourceBundleMessageSource to manage message bundles and translations.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Configuration: Indicates that this class declares one or more Spring beans.</li>
 *   <li>@Bean: Marks the method as a bean producer for the Spring container.</li>
 * </ul>
 */
@Configuration
public class MessageSourceConfig {

    /**
     * Configures the message source for internationalization support.
     * <p>
     * This method sets up a ResourceBundleMessageSource with the following properties:
     * <ul>
     *   <li>Base names: "messages" and "validation" to support multiple resource bundles.</li>
     *   <li>Default encoding: UTF-8 for proper handling of special characters.</li>
     *   <li>Default locale: English (Locale.ENGLISH) as the fallback language.</li>
     *   <li>Use code as default message: Enables returning the message code if no translation is found.</li>
     *   <li>Fallback to system locale: Disabled to prevent unexpected locale fallbacks.</li>
     *   <li>Cache seconds: 3600 seconds (1 hour) for improved performance by caching message lookups.</li>
     * </ul>
     *
     * @return A configured ResourceBundleMessageSource bean for managing message bundles.
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/messages", "i18n/validation");  // Support multiple base names
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.ENGLISH);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(false);  // Prevent unexpected fallbacks
        messageSource.setCacheSeconds(3600);  // Add caching for better performance
        return messageSource;
    }
}