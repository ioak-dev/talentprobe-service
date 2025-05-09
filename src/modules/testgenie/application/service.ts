import * as Helper from "./helper";

export const getAllApp = async (req: any, res: any) => {
  const space = req.params.space;
  const response = await Helper.getAllApp(space);
  res.status(200);
  res.send(response);
  res.end();
};

export const updateAppById = async (req: any, res: any) => {
  const space = req.params.space;
  const response: any = await Helper.updateAppById(
    space,
    req.params.id,
    req.body
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const createApp = async (req: any, res: any) => {
  const space = req.params.space;
  const response: any = await Helper.createApp(space, req.body);
  res.status(200);
  res.send(response);
  res.end();
};

export const deleteAppById = async (req: any, res: any) => {
  const space = req.params.space;
  const response: any = await Helper.deleteAppById(
    space,
    req.params.id
  );
  res.status(200);
  res.send(response);
  res.end();
};


export const getAppById = async (req: any, res: any) => {
  const space = req.params.space;
  const response: any = await Helper.getAppById(
    space,
    req.params.id
  );
  res.status(200);
  res.send(response);
  res.end();
};