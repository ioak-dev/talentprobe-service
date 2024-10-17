import * as Helper from "../consolidatedtestcase/helper";



export const generateConsolidatedTestCases = async (req: any, res: any) => {
  const response: any = await Helper.generateConsolidatedTestCases(
      req.params.suiteId
  );
  res.status(200);
  res.send(response);
  res.end();
};


export const getAllConsolidatedTestCase = async (req: any, res: any) => {
  console.log('getAllConsolidatedTestCase    '+req.params.suiteId);
  const response: any = await Helper.getAllConsolidatedTestCase(req.params.suiteId);
  res.status(response ? 200 : 404);
  res.send(response);
  res.end();
};
