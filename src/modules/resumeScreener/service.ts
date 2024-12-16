import {analyzeResumeService} from "./helper";
import path from 'path';

export const uploadResume = async(req: any, res: any) => {
  if (!req.file) {
    res.status(400).send("No resume file uploaded");
    return;
  }
  const resumePath = path.join("uploads", req.file.filename);

  const analysisResults = await analyzeResumeService(resumePath);
  res.status(200);
  res.send(analysisResults);
  res.end();
}
