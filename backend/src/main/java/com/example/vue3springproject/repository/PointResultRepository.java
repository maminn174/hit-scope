package com.example.vue3springproject.repository;

import com.example.vue3springproject.entity.PointResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointResultRepository
        extends JpaRepository<PointResult, Long> {
}
