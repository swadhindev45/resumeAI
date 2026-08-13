package com.resume.resumeAnalyzer.dto;

import java.util.List;

public class AnalysisResponse {

    private List<String> resume_keywords;
    private List<String> job_keywords;

    private List<String> resume_skills;
    private List<String> required_skills;

    private List<String> matched_skills;
    private List<String> missing_skills;

    private int skill_match_score;

    public AnalysisResponse() {
    }

    public List<String> getResume_keywords() {
        return resume_keywords;
    }

    public void setResume_keywords(List<String> resume_keywords) {
        this.resume_keywords = resume_keywords;
    }

    public List<String> getJob_keywords() {
        return job_keywords;
    }

    public void setJob_keywords(List<String> job_keywords) {
        this.job_keywords = job_keywords;
    }

    public List<String> getResume_skills() {
        return resume_skills;
    }

    public void setResume_skills(List<String> resume_skills) {
        this.resume_skills = resume_skills;
    }

    public List<String> getRequired_skills() {
        return required_skills;
    }

    public void setRequired_skills(List<String> required_skills) {
        this.required_skills = required_skills;
    }

    public List<String> getMatched_skills() {
        return matched_skills;
    }

    public void setMatched_skills(List<String> matched_skills) {
        this.matched_skills = matched_skills;
    }

    public List<String> getMissing_skills() {
        return missing_skills;
    }

    public void setMissing_skills(List<String> missing_skills) {
        this.missing_skills = missing_skills;
    }

    public int getSkill_match_score() {
        return skill_match_score;
    }

    public void setSkill_match_score(int skill_match_score) {
        this.skill_match_score = skill_match_score;
    }
}