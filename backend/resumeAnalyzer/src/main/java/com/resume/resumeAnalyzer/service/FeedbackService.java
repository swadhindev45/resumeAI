package com.resume.resumeAnalyzer.service;

import com.resume.resumeAnalyzer.dto.AnalysisResponse;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    public String generateFeedback(AnalysisResponse response) {

        int score = response.getSkill_match_score();

        if (score >= 80) {

            return "Excellent match. Your resume contains most "
                    + "of the skills required for this job.";

        } else if (score >= 60) {

            return "Good match. Your resume matches several "
                    + "important skills, but there are some areas "
                    + "that could be improved.";

        } else if (score >= 40) {

            return "Moderate match. Consider improving your resume "
                    + "by highlighting more skills relevant to this job.";

        } else {

            return "Low match. Your resume is missing several "
                    + "important skills required for this position.";
        }
    }
}