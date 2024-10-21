import * as Helper from "./helper";

export const getResume = async (req: any, res: any) => {
  const response: any[] = await Helper.getResume();
  res.status(200);
  res.send(response);
  res.end();
};

export const getResumeById = async (req: any, res: any) => {
  const response: any = await Helper.getResumeById(req.params.id);
  res.status(response ? 200 : 404);
  res.send(response);
  res.end();
};


export const scanResume = async (req: any, res: any) => {
  const response: any = await Helper.scanResume(req.file);
  res.status(200);
  res.send(response);
  res.end();
};