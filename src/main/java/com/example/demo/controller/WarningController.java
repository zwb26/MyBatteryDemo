package com.example.demo.controller;

import com.example.demo.service.WarningService;
import com.example.demo.vo.WarningRequest;
import com.example.demo.vo.WarningResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WarningController {

    @Autowired
    private WarningService warningService;

    @PostMapping("/warn")
    public ResponseEntity<List<WarningResponse>> processWarnings(@RequestBody List<WarningRequest> requests) {
        List<WarningResponse> responses = warningService.processWarnings(requests);
        return ResponseEntity.ok(responses);
    }
}
