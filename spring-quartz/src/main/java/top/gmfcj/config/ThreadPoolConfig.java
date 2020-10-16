package top.gmfcj.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {
    private int corePoolSize = 1;
    private int maxPoolSize = 10;
    private int queueCapacity = 100;
    private int keepAliveSeconds = 300;

    public ThreadPoolConfig() {
    }

    @Bean(
            name = {"threadPoolTaskExecutor"}
    )
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(this.maxPoolSize);
        executor.setCorePoolSize(this.corePoolSize);
        executor.setQueueCapacity(this.queueCapacity);
        executor.setKeepAliveSeconds(this.keepAliveSeconds);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean(
            name = {"scheduledExecutorService"}
    )
    protected ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(this.corePoolSize, (new Builder()).namingPattern("schedule-pool-%d").daemon(true).build());
    }
}
