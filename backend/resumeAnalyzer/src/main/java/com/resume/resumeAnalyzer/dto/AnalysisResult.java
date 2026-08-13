package com.resume.resumeAnalyzer.dto;

import java.util.List;

public class AnalysisResult {

    private int score;

    private List<String> matchedSkills;

    private List<String> missingSkills;

    private String feedback;

    public AnalysisResult(
            int score,
            List<String> matchedSkills,
            List<String> missingSkills,
            String feedback) {

        this.score = score;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.feedback = feedback;
    }

    public int getScore() {
        return score;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public String getFeedback() {
        return feedback;
    }
}