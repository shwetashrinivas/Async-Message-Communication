package com.javausecase.asyncexample;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties so that if there are more attributes in the response, 
//they can be safely ignored by Spring.

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieModel {

    private String title;
    private String producer;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Override
    public String toString() {
        return String.format("MovieModel{title='%s', producer='%s'}", title, producer);
    }
}
