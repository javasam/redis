package com.example.redisdemo.repository;

import com.example.redisdemo.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private static final String KEY = "Movie";

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, String> hashOperations;

    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void add(final Movie movie) {
        hashOperations.put(KEY, movie.getId(), movie.getName());
    }

    public void delete(final String id) {
        hashOperations.delete(KEY, id);
    }

    public Movie findMovie(final String id) {
        String movieName = hashOperations.get(KEY, id);
        if (Objects.isNull(movieName)) {
            return new Movie("", "");
        } else {
            return new Movie(id, movieName);
        }

    }

    public Map<String, String> findAllMovies() {
        return hashOperations.entries(KEY);
    }
}
