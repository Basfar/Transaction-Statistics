package com.Mukhtar.seerbitinterview.controller;

import com.Mukhtar.seerbitinterview.model.Statistics;
import com.Mukhtar.seerbitinterview.service.StatisticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {



     // return get stats based on transactions happened in last 30 seconds

    @Autowired
    private StatisticsServiceImpl statisticsService;

    @GetMapping("/statistics")
    public ResponseEntity<Statistics> getStatistics() {
        return ResponseEntity.status(HttpStatus.OK).body(statisticsService.getStatistics());
    }
}
