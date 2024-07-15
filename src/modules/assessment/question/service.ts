import * as Helper from "./helper";

export const getAssessmentQuestion = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any[] = await Helper.getAssessmentQuestion(req.params.id);
  res.status(200);
  res.send(response);
  res.end();
};

export const updateAssessmentQuestion = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any = await Helper.updateAssessmentQuestion(
    req.params.id,
    req.params.questionId,
    req.body,
    userId
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const createAssessmentQuestion = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any = await Helper.createAssessmentQuestion(req.body);
  res.status(200);
  res.send(response);
  res.end();
};

export const deleteAssessmentQuestion = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any = await Helper.deleteAssessmentQuestion(
    req.params.id,
    req.params.questionId
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const generateQuestions = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any = await Helper.generateQuestions(
    req.params.id,
    req.params.count,
    req.body.text
  );
  console.log(response);
  res.status(200);
  res.send({ response });
  res.end();
};
