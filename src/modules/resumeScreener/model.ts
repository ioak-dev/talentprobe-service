import { Gemini } from "aihub";
const {GEMINI_API_KEY} = require("../../../env.js");
export async function analyzeResume(resumeContent: string) {

    const system_prompt = "You are an experienced Human Resource Manager with expertise in the field of any one job role from Machine Learning, Data Science, Full Stack, Web Development, Android Development, App Development, DevOps, Data Analytics, and Big Data Engineering. Your role is to scrutinize this resume in light of the job description provided. Share your insights on the candidate's suitability for the role from an HR perspective. What are the strongest and weakest points you find about the candidate which can determine their shortlisting or rejection. What is the total experience of the candidate, how many total years and months candidate has worked, ignore the education timeline in this calculation. Also provide the industry normalized experience for the candidate, which means, out of the total experience that the candidate has, how much time candidate has actually spent working in an industry. Provide the analysis of the resume in the following JSON format:{'overallAssessment': 'string','strengths': ['string1', 'string2','string3'],'weaknesses': ['string1', 'string2', 'string3'],'recommendation': 'string', 'total experience': 'string', 'industry normalized experience':'string'}.Only return the JSON object, and do not include any additional text outside the JSON format.Provide your response in valid JSON format using double quotes for all keys and values. Ensure there are no additional text or formatting issues.";
    
    try {
        const payload = {
            model: "gemini-1.0-pro",
            contents: [
                {
                    role: 'model',
                    parts: [{ text: system_prompt }]
                },
                {
                    role: 'user',
                    parts: [{ text: resumeContent }]
                }
            ],
            generationConfig: {
                maxOutputTokens: 3071,
                temperature: 0.5
            }
        };

        try {
            const result = await Gemini.process(
                GEMINI_API_KEY,
                "/v1beta/models/gemini-1.5-flash:generateContent",
                payload,
                "object"
            );
            return result.responseObject;
        } catch (error) {
            console.error('Error analyzing resume:', error);
            return "An error occurred while analyzing the resume.";
        }
        
    } catch (error) {
        console.error('Error:', error);
    }     
};