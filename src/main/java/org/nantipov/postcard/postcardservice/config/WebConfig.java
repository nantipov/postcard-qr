package org.nantipov.postcard.postcardservice.config;

import org.nantipov.postcard.postcardservice.services.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LogInterceptor logInterceptor;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public WebConfig(LogInterceptor logInterceptor,
                     ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.logInterceptor = logInterceptor;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/postcard/*/*");
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(threadPoolTaskExecutor);
    }
}
