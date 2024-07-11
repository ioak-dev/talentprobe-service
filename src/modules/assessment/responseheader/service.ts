import * as Helper from "./helper";

export const getAssessmentResponseheader = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any[] = await Helper.getAssessmentResponseheader(req.params.id);
  res.status(200);
  res.send(response);
  res.end();
};

export const updateAssessmentResponseheader = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any = await Helper.updateAssessmentResponseheader(
    req.params.id,
    req.params.questionId,
    req.body,
    userId
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const createAssessmentResponseheader = async (req: any, res: any) => {
  const response: any = await Helper.createAssessmentResponseheader(req.params.id, req.body);
  res.status(200);
  res.send(response);
  res.end();
};

export const deleteAssessmentResponseheader = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const response: any = await Helper.deleteAssessmentResponseheader(
    req.params.id,
    req.params.questionId
  );
  res.status(200);
  res.send(response);
  res.end();
};
