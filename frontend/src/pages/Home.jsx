import { useState } from "react";
import {
  uploadResume,
  analyzeResume,
} from "../services/api";

function Home() {

  const [file, setFile] = useState(null);
  const [jobDescription, setJobDescription] = useState("");

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [result, setResult] = useState(null);

  const handleFileChange = (event) => {

    const selectedFile = event.target.files[0];

    if (!selectedFile) return;

    if (selectedFile.type !== "application/pdf") {
      setError("Please upload a PDF file.");
      return;
    }

    setError("");
    setFile(selectedFile);
  };

  const handleSubmit = async (event) => {

    event.preventDefault();

    setError("");
    setResult(null);

    if (!file) {
      setError("Please upload your resume.");
      return;
    }

    if (!jobDescription.trim()) {
      setError("Please enter the job description.");
      return;
    }

    try {

      setLoading(true);

      const uploadResult =
        await uploadResume(file);

      const analysisResult =
        await analyzeResume(
          uploadResult.id,
          jobDescription
        );

      setResult(analysisResult);

    } catch (err) {

      console.error(err);

      setError(
        err.response?.data ||
        "Unable to analyze the resume."
      );

    } finally {

      setLoading(false);
    }
  };

  const resetAnalyzer = () => {

    setFile(null);
    setJobDescription("");
    setResult(null);
    setError("");
  };

  return (
    <div className="app">

      <header className="navbar">

        <div className="logo">
          Resume<span>AI</span>
        </div>

        <div className="nav-text">
          AI-Powered Resume Analyzer
        </div>

      </header>

      <main className="main">

        {!result ? (

          <section className="hero">

            <div className="hero-text">

              <div className="badge">
                ✦ AI Resume Analysis
              </div>

              <h1>
                Know how well your
                <span> resume fits.</span>
              </h1>

              <p>
                Upload your resume and compare it
                against any job description to discover
                your strengths and missing skills.
              </p>

            </div>

            <form
              className="analyzer-card"
              onSubmit={handleSubmit}
            >

              <div className="upload-section">

                <label className="section-label">
                  Resume
                </label>

                <label className="upload-box">

                  <input
                    type="file"
                    accept=".pdf"
                    onChange={handleFileChange}
                  />

                  {file ? (

                    <div className="file-selected">

                      <div className="file-icon">
                        PDF
                      </div>

                      <div>
                        <strong>
                          {file.name}
                        </strong>

                        <small>
                          Ready for analysis
                        </small>
                      </div>

                    </div>

                  ) : (

                    <>
                      <div className="upload-icon">
                        ↑
                      </div>

                      <strong>
                        Upload your resume
                      </strong>

                      <small>
                        PDF files only
                      </small>
                    </>

                  )}

                </label>

              </div>

              <div className="description-section">

                <label
                  className="section-label"
                  htmlFor="jobDescription"
                >
                  Job Description
                </label>

                <textarea
                  id="jobDescription"
                  value={jobDescription}
                  onChange={(e) =>
                    setJobDescription(e.target.value)
                  }
                  placeholder="Paste the job description here..."
                  rows={9}
                />

              </div>

              {error && (
                <div className="error">
                  {error}
                </div>
              )}

              <button
                className="analyze-button"
                type="submit"
                disabled={loading}
              >

                {loading ? (
                  <>
                    <span className="spinner"></span>
                    Analyzing...
                  </>
                ) : (
                  <>
                    Analyze Resume
                    <span>→</span>
                  </>
                )}

              </button>

            </form>

          </section>

        ) : (

          <section className="results">

            <button
              className="back-button"
              onClick={resetAnalyzer}
            >
              ← Analyze another resume
            </button>

            <div className="results-header">

              <div>
                <div className="badge">
                  ✦ Analysis Complete
                </div>

                <h1>
                  Your resume analysis
                </h1>

                <p>
                  Here's how your resume compares
                  with the job description.
                </p>
              </div>

              <div className="score-card">

                <div className="score">
                  {result.score}%
                </div>

                <span>
                  Skill Match
                </span>

              </div>

            </div>

            <div className="results-grid">

              <div className="result-card">

                <h3>
                  ✓ Matched Skills
                </h3>

                <div className="skills">

                  {result.matchedSkills?.map(
                    (skill) => (
                      <span
                        className="skill matched"
                        key={skill}
                      >
                        {skill}
                      </span>
                    )
                  )}

                </div>

              </div>

              <div className="result-card">

                <h3>
                  ! Missing Skills
                </h3>

                <div className="skills">

                  {result.missingSkills?.map(
                    (skill) => (
                      <span
                        className="skill missing"
                        key={skill}
                      >
                        {skill}
                      </span>
                    )
                  )}

                </div>

              </div>

            </div>

            <div className="feedback-card">

              <div className="feedback-icon">
                ✦
              </div>

              <div>

                <h3>
                  AI Feedback
                </h3>

                <p>
                  {result.feedback}
                </p>

              </div>

            </div>

          </section>

        )}

      </main>

    </div>
  );
}

export default Home;