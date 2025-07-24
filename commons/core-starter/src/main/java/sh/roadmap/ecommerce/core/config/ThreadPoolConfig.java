package sh.roadmap.ecommerce.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * Configuration class for setting up thread pools in the application.
 * Provides multiple thread pool executors for different use cases such as default tasks, IO-intensive tasks,
 * CPU-intensive tasks, and scheduled tasks.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@EnableAsync: Enables asynchronous method execution.</li>
 *   <li>@Configuration: Marks this class as a source of bean definitions.</li>
 * </ul>
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);

    /**
     * Core pool size for the thread pool. Default value is 10.
     */
    @Value("${thread.pool.core-pool-size:10}")
    private int corePoolSize;

    /**
     * Maximum pool size for the thread pool. Default value is 50.
     */
    @Value("${thread.pool.max-pool-size:50}")
    private int maxPoolSize;

    /**
     * Queue capacity for the thread pool. Default value is 100.
     */
    @Value("${thread.pool.queue-capacity:100}")
    private int queueCapacity;

    /**
     * Keep-alive time for idle threads in the pool, in seconds. Default value is 60.
     */
    @Value("${thread.pool.keep-alive-seconds:60}")
    private int keepAliveSeconds;

    /**
     * Flag to allow core threads to time out. Default value is true.
     */
    @Value("${thread.pool.allow-core-thread-timeout:true}")
    private boolean allowCoreThreadTimeout;

    /**
     * Flag to wait for tasks to complete on shutdown. Default value is true.
     */
    @Value("${thread.pool.wait-for-tasks-to-complete:true}")
    private boolean waitForTasksToComplete;

    /**
     * Creates a default task executor with a CallerRunsPolicy for rejected tasks.
     *
     * @return A configured {@link Executor} instance.
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = createBaseExecutor();
        executor.setThreadNamePrefix("default-task-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        logger.info("Created default task executor with core pool size: {}, max pool size: {}",
                corePoolSize, maxPoolSize);

        return executor;
    }

    /**
     * Creates an IO task executor with a custom rejected execution handler.
     *
     * @return A configured {@link Executor} instance.
     */
    @Bean(name = "ioTaskExecutor")
    public Executor ioTaskExecutor() {
        ThreadPoolTaskExecutor executor = createBaseExecutor();
        executor.setCorePoolSize(corePoolSize * 2); // I/O tasks benefit from more threads
        executor.setMaxPoolSize(maxPoolSize * 2);
        executor.setQueueCapacity(queueCapacity * 2);
        executor.setThreadNamePrefix("io-task-");
        executor.setRejectedExecutionHandler(new CustomRejectedExecutionHandler());
        executor.initialize();

        logger.info("Created I/O task executor with core pool size: {}, max pool size: {}",
                corePoolSize * 2, maxPoolSize * 2);

        return executor;
    }

    /**
     * Creates a CPU task executor optimized for CPU-intensive tasks.
     *
     * @return A configured {@link Executor} instance.
     */
    @Bean(name = "cpuTaskExecutor")
    public Executor cpuTaskExecutor() {
        // For CPU-bound tasks, limit threads to available processors
        int availableProcessors = Runtime.getRuntime().availableProcessors();

        ThreadPoolTaskExecutor executor = createBaseExecutor();
        executor.setCorePoolSize(availableProcessors);
        executor.setMaxPoolSize(availableProcessors * 2);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("cpu-task-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();

        logger.info("Created CPU task executor with core pool size: {}, max pool size: {}",
                availableProcessors, availableProcessors * 2);

        return executor;
    }

    /**
     * Creates a base thread pool executor with common configurations.
     *
     * @return A partially configured {@link ThreadPoolTaskExecutor} instance.
     */
    private ThreadPoolTaskExecutor createBaseExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setAllowCoreThreadTimeOut(allowCoreThreadTimeout);
        executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToComplete);

        return executor;
    }

    /**
     * Creates a scheduled task executor for periodic tasks.
     *
     * @return A configured {@link ExecutorService} instance.
     */
    @Bean(name = "scheduledTaskExecutor")
    public ExecutorService scheduledTaskExecutor() {
        ExecutorService executor = Executors.newScheduledThreadPool(corePoolSize);
        logger.info("Created scheduled task executor with pool size: {}", corePoolSize);
        return executor;
    }

    /**
     * Custom rejected execution handler that logs rejected tasks and attempts to execute them in the caller thread.
     */
    static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        private static final Logger rejectionLogger = LoggerFactory.getLogger("ThreadRejectionLogger");

        /**
         * Handles rejected tasks by logging the rejection and attempting to execute the task in the caller thread.
         *
         * @param r        The runnable task that was rejected.
         * @param executor The thread pool executor that rejected the task.
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            rejectionLogger.warn("Task rejected: queue size={}, active threads={}, completed tasks={}",
                    executor.getQueue().size(), executor.getActiveCount(), executor.getCompletedTaskCount());
            // Try to run in the caller's thread as a fallback
            if (!executor.isShutdown()) {
                try {
                    r.run();
                    rejectionLogger.info("Task executed in caller thread after rejection");
                } catch (Exception e) {
                    rejectionLogger.error("Failed to execute rejected task in caller thread", e);
                    throw new RuntimeException("Task execution rejected", e);
                }
            }
        }
    }
}
