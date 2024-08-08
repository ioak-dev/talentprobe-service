import * as Helper from "./helper";


export const getAllUseCase = async (req: any, res: any) => {
//  const userId = req.user.user_id;
  const response: any[] = await Helper.getAllUseCase();
  res.status(200);
  res.send(response);
  res.end();
};



export const updateUseCaseById = async (req: any, res: any) => {
  const response: any = await Helper.updateUseCaseById(
    req.params.suiteId,
    req.params.id,
    req.body
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const createUseCase = async (req: any, res: any) => {
  const response: any = await Helper.createUseCase(req.body);
  res.status(200);
  res.send(response);
  res.end();
};



export const getUseCaseById = async (req: any, res: any) => {
  const response: any = await Helper.getUseCaseById(
    req.params.suiteId,
    req.params.id
  );
  res.status(200);
  res.send(response);
  res.end();
};


export const deleteUseCaseById = async (req: any, res: any) => {
    const response: any = await Helper.deleteUseCaseById(
      req.params.suiteId,
      req.params.id
    );
    res.status(200);
    res.send(response);
    res.end();
  };