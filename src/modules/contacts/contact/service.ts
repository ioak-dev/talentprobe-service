import * as Helper from "./helper";

export const getContact = async (req: any, res: any) => {
  const response: any[] = await Helper.getContact();
  res.status(200);
  res.send(response);
  res.end();
};

export const getContactById = async (req: any, res: any) => {
  const response: any = await Helper.getContactById(
    req.params.id
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const updateContact = async (req: any, res: any) => {
  const response: any = await Helper.updateContact(
    req.params.id,
    req.body
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const createContact = async (req: any, res: any) => {
  const response: any = await Helper.createContact(req.body);
  res.status(200);
  res.send(response);
  res.end();
};

export const deleteContact = async (req: any, res: any) => {
  const response: any = await Helper.deleteContact(
    req.params.id,
    req.params.questionId
  );
  res.status(200);
  res.send(response);
  res.end();
};
