import { analyzeResume } from "./model";
import fs from 'fs-extra';
import pdfParse from 'pdf-parse';


export async function extractTextFromPDF(filePath: string): Promise<string> {
    const dataBuffer = await fs.readFileSync(filePath);
    const pdfData = await pdfParse(dataBuffer);
    const result: string = pdfData.text;
    let plainText = result
        .replace(/[\n\r]+/g, ' ')                       
        .replace(/\s+/g, ' ')                           
        .replace(/https?:\/\/\S+/g, '')                 
        .replace(/[\u2013\u2014]/g, '-')                
        .replace(/['"\u201C\u201D\u2018\u2019]/g, '')   
        .replace(/•/g, '.')                             
        .replace(/\s+/g, ' ')                           
        .trim();
    return await plainText;
}

export async function analyzeResumeService(resumePath: string): Promise<any> {
    const resumeText = await extractTextFromPDF(resumePath);

    const analysisResults = await analyzeResume(resumeText);
    return await analysisResults;
}
