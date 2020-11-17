package com.example.redisdemo.repository;

import com.example.redisdemo.model.Movie;

import java.util.Map;

public interface RedisRepository {

    /**
     * Return all movies
     */
    Map<String, String> findAllMovies();

    /**
     * Add key-value pair to Redis.
     */
    void add(Movie movie);

    /**
     * Delete a key-value pair in Redis.
     */
    void delete(String id);

    /**
     * find a movie
     */
    Movie findMovie(String id);
}
