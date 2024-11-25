import  {GoogleGenerativeAI}  from '@google/generative-ai';
const API_KEY = "AIzaSyDK79pRO4VvmaYpImCd8rJowlIVy2bC-Cw";

export async function analyzeResume(resumeContent: string) {
    const genAI = new GoogleGenerativeAI(API_KEY);
    const model = await genAI.getGenerativeModel({ model: "gemini-1.5-flash" });

    const system_prompt = "You are an experienced Human Resource Manager with expertise in the field of any one job role from Machine Learning, Data Science, Full Stack, Web Development, Android Development, App Development, DevOps, Data Analytics, and Big Data Engineering. Your role is to scrutinize this resume in light of the job description provided. Share your insights on the candidate's suitability for the role from an HR perspective. What are the strongest and weakest points you find about the candidate which can determine their shortlisting or rejection.";
    
    try {
        const result = await model.generateContent({
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
                maxOutputTokens: 1000,
                temperature: 0.5
            }
        });

        console.log(result.response.text());
        return await result.response.text();
    } catch (error) {
        console.error('Error:', error);
    } 
    // console.log(genAI)
}

// const result = analyzeResume("John Doe is a dynamic, results-driven professional with over five years of experience in project management and team leadership. With a proven track record in data analysis, process optimization, and strategic planning, he seeks to leverage these skills to drive operational efficiency and support company goals. In his current role as a Project Manager at ABC Corporation since June 2019, he has led cross-functional teams of over 10 members, successfully delivering more than 15 projects that improved operational efficiency by 30% and consistently met deadlines. John has analyzed project performance data to optimize resource allocation, achieving annual cost savings of over $200,000, and developed a new project tracking system that reduced average project cycle times by 25%. Previously, he served as a Business Analyst at XYZ Inc. from January 2017 to May 2019, where he conducted in-depth data analysis to support key business decisions, boosting revenue by 20%, and prepared comprehensive reports for stakeholders, contributing to a 15% improvement in customer satisfaction. He also streamlined data collection processes, reducing reporting time by 40%. John holds a Bachelor of Science in Business Administration from State University, where he graduated with a GPA of 3.8 in May 2016. His technical skills include expertise in Agile, Scrum, Excel, SQL, Tableau, and strategic planning, alongside strong communication skills. He is certified as a Project Management Professional (PMP) and Certified Scrum Master (CSM). References are available upon request.")
// console.log(result);

