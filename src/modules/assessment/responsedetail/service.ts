import * as Helper from "./helper";

export const getAssessmentResponsedetail = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any[] = await Helper.getAssessmentResponsedetail(req.params.id);
  res.status(200);
  res.send(response);
  res.end();
};

export const getAssessmentResponsedetailByResponseId= async (req: any, res: any) => {
  const response: any[] = await Helper.getAssessmentResponsedetailByResponseId(req.params.responseId);
  res.status(200);
  res.send(response);
  res.end();
};

export const updateAssessmentResponsedetail = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any = await Helper.updateAssessmentResponsedetail(
    req.params.id,
    req.params.questionId,
    req.body,
    userId
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const createAssessmentResponsedetail = async (req: any, res: any) => {
  const response: any = await Helper.createAssessmentResponsedetail(req.params.id, req.params.responseId, req.body);
  res.status(200);
  res.send(response);
  res.end();
};

export const deleteAssessmentResponsedetail = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any = await Helper.deleteAssessmentResponsedetail(
    req.params.id,
    req.params.questionId
  );
  res.status(200);
  res.send(response);
  res.end();
};
