import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL;

const API = axios.create({
  baseURL: API_URL,
});

export const uploadResume = async (file) => {
  const formData = new FormData();

  formData.append("file", file);

  const response = await API.post(
    "/api/resumes/upload",
    formData
  );

  return response.data;
};

export const analyzeResume = async (
  resumeId,
  jobDescription
) => {
  const response = await API.post(
    `/api/resumes/${resumeId}/analyze`,
    jobDescription,
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );

  return response.data;
};