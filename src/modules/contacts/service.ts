import * as Helper from "./helper";

export const getAssessment = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any[] = await Helper.getAssessment();
  res.status(200);
  res.send(response);
  res.end();
};

export const getAssessmentById = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any = await Helper.getAssessmentById(req.params.id);
  res.status(response ? 200 : 404);
  res.send(response);
  res.end();
};

export const updateAssessment = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any = await Helper.updateAssessment(
    req.params.id,
    req.body,
    userId
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const createAssessment = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any = await Helper.createAssessment(req.body);
  res.status(200);
  res.send(response);
  res.end();
};
