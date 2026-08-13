package com.resume.resumeAnalyzer.service;

import com.resume.resumeAnalyzer.model.Resume;
import com.resume.resumeAnalyzer.repository.ResumeRepository;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume processResume(MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Resume file is empty");
        }

        if (file.getOriginalFilename() == null ||
                !file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Only PDF resumes are supported");
        }

        byte[] data = file.getBytes();

        try (PDDocument document = Loader.loadPDF(data)) {

            PDFTextStripper stripper = new PDFTextStripper();

            String text = stripper.getText(document);

            if (text.isBlank()) {
                throw new IllegalArgumentException(
                        "Could not extract text from resume"
                );
            }

            Resume resume = new Resume(
                    file.getOriginalFilename(),
                    text
            );

            return resumeRepository.save(resume);
        }

    }
    public Resume getResume(Long id) {

        return resumeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Resume not found"
                        )
                );
    }
}