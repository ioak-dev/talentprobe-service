import * as Helper from "./helper";

export const getAllTestCases = async (req: any, res: any) => {
  const response: any[] = await Helper.getAllTestCases(req.params.suiteId,req.params.usecaseId);
  res.status(200);
  console.log(response);
  res.send(response);
  res.end();
};
