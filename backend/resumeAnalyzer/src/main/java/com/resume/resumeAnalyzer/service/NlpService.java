package com.resume.resumeAnalyzer.service;

import com.resume.resumeAnalyzer.dto.AnalysisRequest;
import com.resume.resumeAnalyzer.dto.AnalysisResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NlpService {

    private final RestClient restClient;

    public NlpService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://localhost:8000")
                .build();
    }

    public AnalysisResponse analyze(
            String resumeText,
            String jobDescription) {

        AnalysisRequest request =
                new AnalysisRequest(
                        resumeText,
                        jobDescription
                );

        return restClient
                .post()
                .uri("/analyze")
                .body(request)
                .retrieve()
                .body(AnalysisResponse.class);
    }
}