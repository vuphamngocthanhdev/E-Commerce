package sh.roadmap.ecommerce.feign.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up an Apache HttpClient to be used with Feign.
 * This configuration customizes connection pooling, timeouts, and other HTTP client settings.
 */
@Configuration
public class FeignHttpClientConfig {

    // Maximum total number of connections allowed in the connection pool
    private static final int MAX_CONN_TOTAL = 400;

    // Maximum number of connections allowed per route
    private static final int MAX_CONN_PER_ROUTE = 100;

    // Timeout in milliseconds for requesting a connection from the connection pool
    private static final int CONNECTION_REQUEST_TIMEOUT_MS = 2000;

    // Timeout in milliseconds for waiting for a response from the server
    private static final int RESPONSE_TIMEOUT_MS = 15000;

    // Timeout in milliseconds for establishing a connection to the server
    private static final int CONNECTION_TIMEOUT_MS = 5000;

    /**
     * Creates and configures a CloseableHttpClient bean.
     *
     * @return a configured instance of CloseableHttpClient
     */
    @Bean(destroyMethod = "close")
    public CloseableHttpClient httpClient() {
        // Configure the connection manager with connection pooling and socket settings
        var connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnTotal(MAX_CONN_TOTAL)
                .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
                .setDefaultSocketConfig(
                        SocketConfig.custom()
                                .setSoTimeout(Timeout.ofMilliseconds(CONNECTION_TIMEOUT_MS)) // Set connection timeout
                                .build()
                )
                .build();

        // Configure request-level timeouts
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(CONNECTION_REQUEST_TIMEOUT_MS))
                .setResponseTimeout(Timeout.ofMilliseconds(RESPONSE_TIMEOUT_MS))
                .build();

        // Build and return the HttpClient with the configured settings
        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .evictExpiredConnections() // Remove expired connections
                .evictIdleConnections(Timeout.ofSeconds(30)) // Remove idle connections after 30 seconds
                .disableAutomaticRetries() // Disable automatic retries
                .build();
    }
}