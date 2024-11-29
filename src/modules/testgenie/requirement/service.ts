import * as Helper from "./helper";

export const getRequirementByApp = async (req: any, res: any) => {
  const appId = req.params.id;
  const space = req.params.space;
  const response = await Helper.getRequirementByApp(space, appId);
  res.status(200);
  res.send(response);
  res.end();
};

export const updateRequirementById = async (req: any, res: any) => {
  const space = req.params.space;
  const parameters = req.url.split('/');
  const applicationid = parameters[3];
  const requirementid = parameters[5];
  const response: any = await Helper.updateRequirementById(
    space,
    applicationid,
    requirementid,
    req.body
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const createRequirement = async (req: any, res: any) => {
  req.body.applicationId = req.params.id
  const space = req.params.space;
  const response: any = await Helper.createRequirement(space, req.body);
  res.status(200);
  res.send(response);
  res.end();
};

export const deleteRequirement = async(req:any, res:any) => {
  const space = req.params.space;
  const appId = req.params.id;
  const response: any = await Helper.deleteRequirement(space, appId);
  res.status(200);
  res.send(response);
  res.end();
}

export const deleteRequirementById = async (req: any, res: any) => {
  const space = req.params.space;
  const parameters = req.url.split('/');
  const applicationid = parameters[3];
  const requirementid = parameters[5];
  const response: any = await Helper.deleteRequirementById(
    space,
    applicationid,
    requirementid
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const getRequirementById = async (req: any, res: any) => {
  const space = req.params.space;
  const parameters = req.url.split('/');
  const applicationid = parameters[3];
  const requirementid = parameters[5];
  const response: any = await Helper.getRequirementById(
    space,
    applicationid,
    requirementid
  );
  res.status(200);
  res.send(response);
  res.end();
};