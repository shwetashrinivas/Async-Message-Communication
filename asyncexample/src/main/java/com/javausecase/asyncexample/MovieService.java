package com.javausecase.asyncexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

/*public Movie API which just returns a Movie’s Data*/

/*This class is a @Service which makes it eligible for Spring Component Scan. 
 * The lookForMovie method’s return type is CompletableFuture which is a requirement for any asynchronous service. 
 * As timing for the API can vary, we have added a delay of 2 second for demonstration.*/

@Service
public class MovieService {

    private static final Logger LOG = LoggerFactory.getLogger(MovieService.class);

    private final RestTemplate restTemplate;

    public MovieService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<MovieModel> lookForMovie(String movieId) throws InterruptedException {
        LOG.info("Looking up Movie ID: {}", movieId);
        String url = String.format("https://ghibliapi.herokuapp.com/films/%s", movieId);
        MovieModel results = restTemplate.getForObject(url, MovieModel.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }
}


/*
 * 
 * The Films endpoint returns information about all of the Studio Ghibli films.*/
