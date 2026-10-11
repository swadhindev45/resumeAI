import re


def normalize_text(text: str) -> str:
    text = text.lower()
    text = re.sub(r"\s+", " ", text)
    return text.strip()


def find_skill_mentions(text: str, skill: str) -> bool:
    """
    Check whether a skill appears as a complete phrase.
    """

    normalized_text = normalize_text(text)
    normalized_skill = normalize_text(skill)

    pattern = (
        r"(?<!\w)"
        + re.escape(normalized_skill)
        + r"(?!\w)"
    )

    return bool(re.search(pattern, normalized_text))


def get_project_sections(resume_text: str) -> str:
    """
    Extract text that appears to belong to project sections.

    This is a simple first version. We will improve section
    detection later to handle different resume formats.
    """

    lines = resume_text.splitlines()

    project_headers = {
        "projects",
        "personal projects",
        "academic projects",
        "technical projects",
        "project experience",
    }

    other_headers = {
        "education",
        "skills",
        "technical skills",
        "experience",
        "work experience",
        "certifications",
        "achievements",
        "summary",
        "professional summary",
    }

    in_project_section = False
    project_lines = []

    for line in lines:
        cleaned = line.strip().lower()
        cleaned = re.sub(r"[:\s]+$", "", cleaned)

        if cleaned in project_headers:
            in_project_section = True
            continue

        if cleaned in other_headers:
            in_project_section = False
            continue

        if in_project_section:
            project_lines.append(line)

    return "\n".join(project_lines)


def analyze_skill_evidence(
    resume_text: str,
    required_skills: list[str],
) -> list[dict]:

    project_text = get_project_sections(resume_text)
    evidence_results = []

    implementation_verbs = {
        "built", "developed", "implemented",
        "created", "designed", "integrated",
        "deployed", "configured", "optimized",
        "used", "utilized", "containerized",
        "automated", "managed", "maintained",
        "trained", "analyzed", "processed"
    }

    for skill in sorted(set(required_skills)):
        skill_in_resume = find_skill_mentions(
            resume_text, skill
        )

        skill_in_projects = find_skill_mentions(
            project_text, skill
        )

        if not skill_in_resume:
            status = "MISSING"
            evidence = None

        elif not skill_in_projects:
            status = "MENTIONED"
            evidence = (
                "Skill appears in the resume, but no mention "
                "was found in the detected project section."
            )

        else:
            demonstrated = False

            for line in project_text.splitlines():
                if not find_skill_mentions(line, skill):
                    continue

                words = set(normalize_text(line).split())

                if words & implementation_verbs:
                    demonstrated = True
                    break

            if demonstrated:
                status = "DEMONSTRATED"
                evidence = (
                    "Project description contains the skill "
                    "alongside an implementation-related verb."
                )
            else:
                status = "MENTIONED"
                evidence = (
                    "Skill appears in a project section, but no "
                    "implementation-related wording was detected "
                    "on the same line."
                )

        evidence_results.append({
            "skill": skill,
            "status": status,
            "evidence": evidence
        })

    return evidence_results