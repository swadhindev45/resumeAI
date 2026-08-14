package com.resume.resumeAnalyzer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnalysisRequest {

    @JsonProperty("resume_text")
    private String resume_text;

    @JsonProperty("job_description")
    private String job_description;

    public AnalysisRequest() {
    }

    public AnalysisRequest(String resume_text, String job_description) {
        this.resume_text = resume_text;
        this.job_description = job_description;
    }

    public String getResume_text() {
        return resume_text;
    }

    public void setResume_text(String resume_text) {
        this.resume_text = resume_text;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }
}