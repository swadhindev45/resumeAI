from skill_evidence import analyze_skill_evidence, get_project_sections

resume_text = """
Technical Skills:
Java, Docker

Projects:
Notification System
Developed REST APIs using Java.
Containerized the application using Docker.
"""

required_skills = ["Java", "Docker", "AWS"]

print("=== DETECTED PROJECT SECTION ===")
print(repr(get_project_sections(resume_text)))

print("\n=== SKILL EVIDENCE RESULTS ===")
print("Required skills:", required_skills)
print("Number of skills:", len(required_skills))
results = analyze_skill_evidence(resume_text, required_skills)

for result in results:
    print(result)