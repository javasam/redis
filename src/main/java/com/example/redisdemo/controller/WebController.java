package com.example.redisdemo.controller;

import com.example.redisdemo.model.Movie;
import com.example.redisdemo.repository.RedisRepository;
import com.example.redisdemo.repository.RedisRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class WebController {

    @Autowired
    private RedisRepository redisRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/keys")
    public @ResponseBody
    Map<String, String> keys() {
        return redisRepository.findAllMovies();
    }

    @RequestMapping("/values")
    public @ResponseBody
    Map<String, String> findAll() {
        Map<String, String> allMovies = redisRepository.findAllMovies();
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> entry : allMovies.entrySet()) {
            String key = entry.getKey();
            map.put(key, allMovies.get(key));
        }
        return map;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> add(
            @RequestParam String key,
            @RequestParam String value) {

        Movie movie = new Movie(key, value);

        redisRepository.add(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@RequestParam String key) {
        redisRepository.delete(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // GET -> http://localhost:8080/findbyid?id=1
    @RequestMapping("/findbyid")
    public @ResponseBody
    Optional<String> find(@RequestParam(value = "id") String id) {
        if (id != null) {
            return Optional.of(redisRepository.findMovie(id).getName());
        }
        return Optional.empty();
    }


}
