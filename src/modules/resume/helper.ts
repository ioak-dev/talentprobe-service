import { getGlobalCollection } from "../../lib/dbutils";
import {
  resumeCollection,
  resumeSchema,
} from "./model";
import * as Gptutils from "../../lib/gptutils";
import { getResumePrompt } from "./prompt";
import * as path from "path";
import pdfParse from "pdf-parse";
import mammoth from "mammoth";

export const getResume = async () => {
  const model = getGlobalCollection(resumeCollection,resumeSchema);
  return await model.find({});
};


export const getResumeById = async (id: string) => {
  const model = getGlobalCollection(resumeCollection, resumeSchema);
  const response = await model.find({ _id: id });
  if (response.length > 0) {
    return response[0];
  }
  return null;
};


export const scanResume = async (file: any) => {
  console.log('scanResume processing started');
  const model = getGlobalCollection(resumeCollection, resumeSchema);
  const extn = path.extname(file.originalname);
  const fileName = file.originalname;
  let content ='';
  try {
    if(extn==='.pdf'){
      const data = await pdfParse(file.buffer);
      content = data.text;
    }
    else if(extn==='.docx'){
      const rawData = await mammoth.extractRawText({buffer:file.buffer});
      content = rawData.value;
    }
    else {
      throw new Error("Unsupported file type");
    }
    const response = await Gptutils.predict(getResumePrompt(content));
    const payload = {
      data: response?.keyPoints,
      attachment: file.buffer,
      filename: fileName,
    };
    console.log('gpt call for resume scan done');
    return await model.create(payload);
  }
  catch (error){
    throw error;
  }
};