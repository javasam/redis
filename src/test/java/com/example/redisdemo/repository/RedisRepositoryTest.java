package com.example.redisdemo.repository;

import com.example.redisdemo.config.RedisConfig;
import com.example.redisdemo.model.Movie;
import com.example.redisdemo.repository.RedisRepository;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.embedded.RedisServerBuilder;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class RedisRepositoryTest {

    @Autowired
    RedisRepository redisRepository;

    private static redis.embedded.RedisServer redisServer;

    @BeforeClass
    public static void startRedisServer() throws IOException {
        redisServer = new RedisServerBuilder().port(6379).setting("maxmemory 128M").build();
        redisServer.start();
    }

    @AfterClass
    public static void stopRedisServer() throws IOException {
        redisServer.stop();
    }

    @Test
    public void whenSaveItem_thenGetItem() {
        Movie movie = new Movie("1", "Alien");
        redisRepository.add(movie);
        Movie getMovie = redisRepository.findMovie("1");
        assertEquals(movie.toString(), getMovie.toString());
    }


}