import * as Helper from "./helper";

export const generateUsecase = async (req: any, res: any) => {
    const params = req.url.split('/');
    const applicationId = params[3];
    const requirementId = params[5];
    const space = req.params.space;
    const response: any = await Helper.generateUsecase(space, requirementId, applicationId);
    res.status(200);
    res.send(response);
    res.end();
};

export const getUsecase = async(req:any, res:any) =>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const space = parameters[1];
    const response: any = await Helper.getUsecase(space, applicationid, requirementid);
    res.status(200);
    res.send(response);
    res.end();
};

export const createUsecase = async(req:any, res:any)=>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const space = parameters[1];
    const response: any = await Helper.createUsecase(space, applicationid, requirementid, req.body);
    res.status(200);
    res.send(response);
    res.end();
};

export const deleteAllUsecase = async(req:any, res:any)=>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const space = parameters[1];
    const response: any = await Helper.deleteAllUsecase(space, applicationid, requirementid);
    res.status(200);
    res.send(response);
    res.end();
};

export const deleteUsecaseById = async(req:any, res:any)=>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const usecaseid = parameters[7];
    const space = parameters[1];
    const response: any = await Helper.deleteUsecaseById(space, applicationid, requirementid, usecaseid);
    res.status(200);
    res.send(response);
    res.end();
};

export const getUsecaseById = async(req:any, res:any)=>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const usecaseid = parameters[7];
    const space = parameters[1];
    const response: any = await Helper.getUsecaseById(space, applicationid, requirementid, usecaseid);
    res.status(200);
    res.send(response);
    res.end();
};

export const updateUsecaseById = async(req:any, res:any)=>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const usecaseid = parameters[7];
    const testcaseid = parameters[9];
    const space = parameters[1];
    const response: any = await Helper.updateUsecaseById(space, applicationid, requirementid, usecaseid, req.body);
    res.status(200);
    res.send(response);
    res.end();
};
