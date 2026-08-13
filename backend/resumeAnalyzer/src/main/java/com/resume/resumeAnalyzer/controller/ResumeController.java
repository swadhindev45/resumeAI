package com.resume.resumeAnalyzer.controller;

import com.resume.resumeAnalyzer.dto.AnalysisResponse;
import com.resume.resumeAnalyzer.dto.AnalysisResult;
import com.resume.resumeAnalyzer.model.Analysis;
import com.resume.resumeAnalyzer.model.Resume;
import com.resume.resumeAnalyzer.repository.AnalysisRepository;
import com.resume.resumeAnalyzer.service.FeedbackService;
import com.resume.resumeAnalyzer.service.NlpService;
import com.resume.resumeAnalyzer.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {
    private final NlpService nlpService;
    private final ResumeService resumeService;
    private final FeedbackService feedbackService;
    private final AnalysisRepository analysisRepository;

    public ResumeController(NlpService nlpService, ResumeService resumeService, FeedbackService feedbackService, AnalysisRepository analysisRepository) {
        this.nlpService = nlpService;
        this.resumeService = resumeService;
        this.feedbackService = feedbackService;
        this.analysisRepository = analysisRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(
            @RequestParam("file") MultipartFile file) {

        try {

            Resume resume = resumeService.processResume(file);

            return ResponseEntity.ok(resume);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity.internalServerError()
                    .body("Failed to process resume");
        }
    }
    @PostMapping("/{id}/analyze")
    public ResponseEntity<?> analyzeResume(
            @PathVariable Long id,
            @RequestBody String jobDescription) {

        try {

            Resume resume = resumeService.getResume(id);

            AnalysisResponse response =
                    nlpService.analyze(
                            resume.getExtractedText(),
                            jobDescription
                    );

            String generatedFeedback =
                    feedbackService.generateFeedback(response);

            String matchedSkills =
                    String.join(
                            ", ",
                            response.getMatched_skills()
                    );

            String missingSkills =
                    String.join(
                            ", ",
                            response.getMissing_skills()
                    );

            Analysis analysis = new Analysis(
                    resume.getId(),
                    response.getSkill_match_score(),
                    matchedSkills,
                    missingSkills,
                    generatedFeedback
            );

            analysisRepository.save(analysis);

            return ResponseEntity.ok(
                    new AnalysisResult(
                            response.getSkill_match_score(),
                            response.getMatched_skills(),
                            response.getMissing_skills(),
                            generatedFeedback
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }
    }