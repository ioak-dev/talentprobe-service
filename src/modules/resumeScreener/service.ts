import {analyzeResumeService} from "./helper";
import path from 'path';

// import express from "express";
// import {extractTextFromPDF} from "./helper";// import multer from "multer";
// import {Request} from "express";
// interface MulterRequest extends Request {
//   file?: Express.Multer.File;
// }
export const uploadResume = async(req: any, res: any) => {
  const resumePath = path.join("uploads", req.file.filename);

  if (!resumePath) {
    res.status(400).send("No resume file uploaded");
    return;
  }
  const analysisResults = await analyzeResumeService(resumePath);
  res.status(200).send(analysisResults);

  // try {
  //   const analysisResults = await analyzeResumeService(resumePath);
  //   res.status(200).send(analysisResults);
  // } catch (error) {
  //   console.error("Error analyzing resume:", error);
  //   res.status(500).send("Error analyzing resume");
  // }
}
