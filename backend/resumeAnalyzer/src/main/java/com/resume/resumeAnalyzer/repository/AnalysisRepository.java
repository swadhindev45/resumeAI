package com.resume.resumeAnalyzer.repository;

import com.resume.resumeAnalyzer.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository
        extends JpaRepository<Analysis, Long> {
}