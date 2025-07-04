package com.arya.kafka.controllers;

import com.arya.kafka.model.SuperHero;
import com.arya.kafka.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    @Autowired
    private ProducerService<SuperHero> producerService;


    @GetMapping(value = "/publish")
    public String sendMessageToKafkaTopic(@RequestParam("message") String message) {
        producerService.sendMessage(message);
        return "Successfully publisher message..!";
    }


    @PostMapping(value = "/publish")
    public Map<String, Object> sendObjectToKafkaTopic(@RequestBody SuperHero superHero) {
        producerService.sendSuperHeroMessage(superHero);

        Map<String, Object> map = new HashMap<>();
        map.put("message", "Successfully publisher Super Hero..!");
        map.put("payload", superHero);
        return map;
    }
    
    @PostMapping(value = "/publish/heros")
    public List<Map<String, Object>> sendObjectsToKafkaTopic(@RequestBody List<SuperHero> superHeros) {

        List<Map<String, Object>> responses = new ArrayList<>();

        for (SuperHero hero : superHeros) {
            producerService.sendSuperHeroMessage(hero);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Successfully published Super Hero!");
            response.put("payload", hero);

            responses.add(response);
        }

        return responses;
    }
    
}
