import { analyzeResume } from "./model";
import fs from 'fs-extra';
import pdfParse from 'pdf-parse';


export async function extractTextFromPDF(filePath: string): Promise<string> {
    const dataBuffer = await fs.readFileSync(filePath);
    const pdfData = await pdfParse(dataBuffer);
    const result: string = pdfData.text;
    return await result;
    // return await result;
}

export async function analyzeResumeService(resumePath: string): Promise<any> {
    const resumeText = await extractTextFromPDF(resumePath);

    const analysisResults = await analyzeResume(resumeText);
    return await analysisResults;
}


// (async () => {
//     const result = analyzeResumeService("./AkankshaResumeUpdated5.pdf");
//     console.log(result); // Logs the result of the `analyzeResume` function
// })();