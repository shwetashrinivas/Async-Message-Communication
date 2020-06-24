package com.javausecase.asyncexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/*@EnableAsync annotation which enables Spring’s ability to run 
Asynchronous methods in a background thread pool.*/

@SpringBootApplication
@EnableAsync
public class AsyncexampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncexampleApplication.class, args).close();
    }

   //Executor allows to define Thread limit 
   
   /* we set that maximum of 2 threads should run concurrently
    the queue size is set to 500. */
    
    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);        
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("AsyncCommunication-");
        executor.initialize();
        return executor;
    }
}
