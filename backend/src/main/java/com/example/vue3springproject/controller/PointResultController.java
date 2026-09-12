package com.example.vue3springproject.controller;

import com.example.vue3springproject.dto.PointRequest;
import com.example.vue3springproject.entity.PointResult;
import com.example.vue3springproject.service.PointResultService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/points")
@CrossOrigin(origins = "http://localhost:5173")
public class PointResultController {

    private final PointResultService pointResultService;

    public PointResultController (PointResultService pointResultService) {
        this.pointResultService = pointResultService;
    }

    @PostMapping
    public ResponseEntity<PointResult> createPoint (@RequestBody PointRequest pointRequest) {
        PointResult result = pointResultService.save(pointRequest);

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<PointResult>> getAllPoints() {
        return ResponseEntity.ok(pointResultService.findAll());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPoints() {
        pointResultService.deleteAll();

        return ResponseEntity.noContent().build();
    }
}