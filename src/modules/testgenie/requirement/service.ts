import * as Helper from "./helper";

const DATE_FORMAT = 'yyyyMMdd_HHmmss';
const HEADER_NAME = 'Content-Disposition';
const HEADER_VALUES = 'attachment; filename=';

export const getRequirementByApp = async (req: any, res: any) => {
  //const userId = req.user.user_id;
  const {appId} = req.params;
  const space = req.params.space;
  const response = await Helper.getRequirementByApp(space, appId);
  res.status(200);
  res.send(response);
  res.end();
};

export const updateRequirementById = async (req: any, res: any) => {
  const space = req.params.space;
  const response: any = await Helper.updateRequirementById(
    space,
    req.params.requirementid,
    req.body
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const createRequirement = async (req: any, res: any) => {
  const space = req.params.space;
  const response: any = await Helper.createRequirement(space, req.body);
  res.status(200);
  res.send(response);
  res.end();
};


export const exportApp = async (req: any, res: any) => {
  try {
    //const { suiteId, type } = req.params;
    const appId= "667ba7051e1dfa57e0b97af6";
    const type="CSV";
    console.log(appId, type);
    const result = await Helper.exportApp(appId, type);

    if ('body' in result) {
      res.status(200).json(result.body);
    } else {
      res.set({
        [HEADER_NAME]: HEADER_VALUES + result.fileName,
        'Content-Length': result.fileContentLength,
        'Content-Type': result.contentType,
      });
      result.resource.pipe(res);
    }
  } catch (error:any) {
    res.status(500).send(error.message);
  }
};

export const deleteRequirement = async(req:any, res:any) => {
  const space = req.params.space;
  const appId = req.params.applicationid;
  const response: any = await Helper.deleteRequirement(space, appId);
  res.status(200);
  res.send(response);
  res.end();
}

export const deleteRequirementById = async (req: any, res: any) => {
  const space = req.params.space;
  const response: any = await Helper.deleteRequirementById(
    space,
    req.params.requirementid
  );
  res.status(200);
  res.send(response);
  res.end();
};


export const getRequirementById = async (req: any, res: any) => {
  const space = req.params.space;
  const response: any = await Helper.getRequirementById(
    space,
    req.params.requirementid
  );
  res.status(200);
  res.send(response);
  res.end();
};