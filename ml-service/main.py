from fastapi import FastAPI
from pydantic import BaseModel
import spacy
import re

from skills import SKILLS


app = FastAPI(
    title="Cloud Resume Analyzer NLP Service",
    version="1.0"
)

nlp = spacy.load("en_core_web_sm")


class AnalyzeRequest(BaseModel):
    resume_text: str
    job_description: str


def normalize_text(text: str):
    text = text.lower()
    text = re.sub(r"\s+", " ", text)
    return text.strip()


def extract_keywords(text: str):

    doc = nlp(text)

    keywords = set()

    for token in doc:

        # Nouns and proper nouns are useful
        # candidates for resume keywords.
        if token.pos_ in {"NOUN", "PROPN"}:

            word = token.lemma_.lower().strip()

            if len(word) > 2:
                keywords.add(word)

    return sorted(keywords)


def extract_skills(text: str):

    normalized = normalize_text(text)

    found_skills = []

    for skill in SKILLS:

        pattern = r"(?<!\w)" + re.escape(skill) + r"(?!\w)"

        if re.search(pattern, normalized):
            found_skills.append(skill)

    return sorted(found_skills)


@app.get("/")
def home():

    return {
        "service": "Resume NLP Service",
        "status": "running"
    }


@app.post("/analyze")
def analyze(request: AnalyzeRequest):

    resume_text = normalize_text(
        request.resume_text
    )

    job_description = normalize_text(
        request.job_description
    )

    # NLP keyword extraction
    resume_keywords = extract_keywords(
        resume_text
    )

    job_keywords = extract_keywords(
        job_description
    )

    # Skill extraction
    resume_skills = extract_skills(
        resume_text
    )

    required_skills = extract_skills(
        job_description
    )

    resume_skill_set = set(resume_skills)
    required_skill_set = set(required_skills)

    matched_skills = sorted(
        resume_skill_set & required_skill_set
    )

    missing_skills = sorted(
        required_skill_set - resume_skill_set
    )

    if required_skill_set:

        skill_score = round(
            len(matched_skills)
            / len(required_skill_set)
            * 100
        )

    else:

        skill_score = 0

    return {

        "resume_keywords": resume_keywords,

        "job_keywords": job_keywords,

        "resume_skills": resume_skills,

        "required_skills": required_skills,

        "matched_skills": matched_skills,

        "missing_skills": missing_skills,

        "skill_match_score": skill_score
    }