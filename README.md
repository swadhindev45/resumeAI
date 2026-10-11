# ResumeAI

ResumeAI is an AI-powered resume analysis platform that analyzes a resume against a job description and provides a skill-match score, matched skills, missing skills, and personalized feedback.

## 🚀 Live Demo

- **Frontend:** https://vercel.com/iq18/resume-ai
- **Backend:** https://resumeai-backend-qvzx.onrender.com
- **ML Service:** https://resumeai-kznk.onrender.com

> The backend and ML service are hosted on Render, while the database is hosted on Railway.

---

## ✨ Features

- 📄 Upload PDF resumes
- 🔍 Extract resume text automatically
- 🤖 AI/ML-powered resume analysis
- 📊 Calculate skill-match score
- ✅ Identify matched skills
- ❌ Identify missing skills
- 💡 Generate personalized resume feedback
- 💾 Store resumes and analysis results
- 🌐 Fully deployed and accessible through the web

---

## 🏗️ Architecture

```text
                    ┌─────────────────────┐
                    │    React Frontend   │
                    │       Vercel        │
                    └──────────┬──────────┘
                               │
                               │ REST API
                               ▼
                    ┌─────────────────────┐
                    │   Spring Boot API   │
                    │       Render        │
                    └───────┬─────┬───────┘
                            │     │
                    ┌───────┘     └────────────┐
                    │                          │
                    ▼                          ▼
          ┌─────────────────┐       ┌─────────────────┐
          │   Railway MySQL │       │   ML/NLP API    │
          │    Database     │       │     Render      │
          └─────────────────┘       └─────────────────┘
