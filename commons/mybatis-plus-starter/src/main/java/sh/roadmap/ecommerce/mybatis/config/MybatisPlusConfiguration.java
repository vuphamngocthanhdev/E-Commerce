package sh.roadmap.ecommerce.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for MyBatis-Plus.
 * This class sets up the MyBatis-Plus interceptor with a pagination inner interceptor.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Configuration: Marks this class as a Spring configuration class.</li>
 *   <li>@Bean: Indicates that the method produces a bean to be managed by the Spring container.</li>
 * </ul>
 */
@Configuration
public class MybatisPlusConfiguration {

    /**
     * Configures the MyBatis-Plus interceptor with a pagination inner interceptor.
     *
     * @return A configured instance of MybatisPlusInterceptor.
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}