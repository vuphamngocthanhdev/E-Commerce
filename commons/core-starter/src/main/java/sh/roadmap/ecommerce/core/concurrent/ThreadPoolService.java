package sh.roadmap.ecommerce.core.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * Service class for managing thread pool operations.
 * Provides methods to execute tasks asynchronously using different thread pools.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@Service: Marks this class as a Spring service component.</li>
 * </ul>
 */
@Component
public class ThreadPoolService {

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolService.class);

    /**
     * The default thread pool executor for general tasks.
     */
    private final Executor defaultExecutor;

    /**
     * The IO thread pool executor for IO-intensive tasks.
     */
    private final Executor ioExecutor;

    /**
     * The CPU thread pool executor for CPU-intensive tasks.
     */
    private final Executor cpuExecutor;

    /**
     * Constructs a ThreadPoolService with the specified executors.
     *
     * @param defaultExecutor The default thread pool executor.
     * @param ioExecutor      The IO thread pool executor.
     * @param cpuExecutor     The CPU thread pool executor.
     */
    public ThreadPoolService(
            @Qualifier("taskExecutor") Executor defaultExecutor,
            @Qualifier("ioTaskExecutor") Executor ioExecutor,
            @Qualifier("cpuTaskExecutor") Executor cpuExecutor) {
        this.defaultExecutor = defaultExecutor;
        this.ioExecutor = ioExecutor;
        this.cpuExecutor = cpuExecutor;
    }

    /**
     * Executes a task asynchronously using the default thread pool.
     *
     * @param task The task to execute.
     * @param <T>  The type of the result produced by the task.
     * @return A CompletableFuture representing the result of the asynchronous computation.
     */
    public <T> CompletableFuture<T> executeAsync(Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, defaultExecutor);
    }

    /**
     * Executes a task asynchronously using the IO thread pool.
     *
     * @param task The task to execute.
     * @param <T>  The type of the result produced by the task.
     * @return A CompletableFuture representing the result of the asynchronous computation.
     */
    public <T> CompletableFuture<T> executeIoAsync(Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, ioExecutor);
    }

    /**
     * Executes a task asynchronously using the CPU thread pool.
     *
     * @param task The task to execute.
     * @param <T>  The type of the result produced by the task.
     * @return A CompletableFuture representing the result of the asynchronous computation.
     */
    public <T> CompletableFuture<T> executeCpuAsync(Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, cpuExecutor);
    }

    /**
     * Runs a Runnable task asynchronously using the default thread pool.
     *
     * @param runnable The task to run.
     * @return A CompletableFuture representing the completion of the task.
     */
    public CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, defaultExecutor);
    }

    /**
     * Logs the status of the thread pools.
     */
    public void logThreadPoolStatus() {
        log.info("Thread pools are active and running");
    }
}
