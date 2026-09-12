package com.example.vue3springproject.service;

import com.example.vue3springproject.entity.PointResult;
import com.example.vue3springproject.repository.PointResultRepository;
import org.springframework.stereotype.Service;
import com.example.vue3springproject.dto.PointRequest;

import java.util.List;

@Service
public class PointResultService {

    private final PointResultRepository pointResultRepository;

    public PointResultService(PointResultRepository pointResultRepository) {
        this.pointResultRepository = pointResultRepository;
    }

    public PointResult save(PointRequest pointRequest) {
        double x = pointRequest.getX();
        double y = pointRequest.getY();
        double r = pointRequest.getR();

        if (!isValidPoint(x, y, r)) {
            throw new IllegalArgumentException("Некорректные координаты или радиус");
        }

        boolean hit = checkHit(x, y, r);

        PointResult pointResult = new PointResult();

        pointResult.setX(x);
        pointResult.setY(y);
        pointResult.setR(r);
        pointResult.setHit(hit);

        return pointResultRepository.save(pointResult);
    }

    public boolean checkHit(double x,  double y, double r) {
        return x * x + y * y <= r * r;
    }

    private boolean isValidPoint(double x, double y, double r) {
        return x>=-5 && x<=5
                && y>=-5 && y<=5
                && r>=1 && r<=5;
    }

    public List<PointResult> findAll() {
        return pointResultRepository.findAll();
    }

    public void deleteAll() {
        pointResultRepository.deleteAll();
    }
}