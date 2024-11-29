import { predictGemini } from "../../lib/geminiutils";

export async function analyzeResume(resumeContent: string) {

    const system_prompt = "You are an experienced Human Resource Manager with expertise in the field of any one job role from Machine Learning, Data Science, Full Stack, Web Development, Android Development, App Development, DevOps, Data Analytics, and Big Data Engineering. Your role is to scrutinize this resume in light of the job description provided. Share your insights on the candidate's suitability for the role from an HR perspective. What are the strongest and weakest points you find about the candidate which can determine their shortlisting or rejection.Provide the analysis of the resume in the following JSON format:{'overallAssessment': 'string','strengths': ['string1', 'string2','string3'],'weaknesses': ['string1', 'string2', 'string3'],'recommendation': 'string'}.Only return the JSON object, and do not include any additional text outside the JSON format.Provide your response in valid JSON format using double quotes for all keys and values. Ensure there are no additional text or formatting issues.";
    
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
            const result = await predictGemini(payload);
            console.log(result);
            return result;
        } catch (error) {
            console.error('Error analyzing resume:', error);
            return "An error occurred while analyzing the resume.";
        }
        
    } catch (error) {
        console.error('Error:', error);
    }     
};