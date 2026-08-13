package com.resume.resumeAnalyzer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "analyses")
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long resumeId;

    private int skillMatchScore;

    @Column(columnDefinition = "TEXT")
    private String matchedSkills;

    @Column(columnDefinition = "TEXT")
    private String missingSkills;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    public Analysis() {
    }

    public Analysis(
            Long resumeId,
            int skillMatchScore,
            String matchedSkills,
            String missingSkills,
            String feedback) {

        this.resumeId = resumeId;
        this.skillMatchScore = skillMatchScore;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public int getSkillMatchScore() {
        return skillMatchScore;
    }

    public String getMatchedSkills() {
        return matchedSkills;
    }

    public String getMissingSkills() {
        return missingSkills;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public void setSkillMatchScore(int skillMatchScore) {
        this.skillMatchScore = skillMatchScore;
    }

    public void setMatchedSkills(String matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public void setMissingSkills(String missingSkills) {
        this.missingSkills = missingSkills;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}