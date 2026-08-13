package com.resume.resumeAnalyzer.repository;

import com.resume.resumeAnalyzer.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}