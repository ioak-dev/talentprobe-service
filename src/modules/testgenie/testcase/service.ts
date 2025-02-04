import * as Helper from "./helper";

export const createTestcase = async(req: any, res: any)=>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const usecaseid = parameters[7];
    const space = req.params.space;
    const response: any = await Helper.createTestcase(space, applicationid, requirementid, usecaseid, req.body);
    res.status(200);
    res.send(response);
    res.end();
};

export const getTestcase = async(req: any, res: any)=>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const usecaseid = parameters[7];
    const space = req.params.space;
    const response: any = await Helper.getTestcase(space, applicationid, requirementid, usecaseid);
    res.status(200);
    res.send(response);
    res.end();
};

export const deleteAllTestcase = async(req:any, res:any)=>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const usecaseid = parameters[7]
    const space = parameters[1];
    const response: any = await Helper.deleteAllTestcase(space, applicationid, requirementid, usecaseid);
    res.status(200);
    res.send(response);
    res.end();
};

export const deleteTestcaseById = async(req:any, res:any)=>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const usecaseid = parameters[7];
    const testcaseid = parameters[9];
    const space = parameters[1];
    const response: any = await Helper.deleteTestcaseById(space, applicationid, requirementid, usecaseid, testcaseid);
    res.status(200);
    res.send(response);
    res.end();
};

export const getTestcaseById = async(req:any, res:any)=>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const usecaseid = parameters[7];
    const testcaseid = parameters[9];
    const space = parameters[1];
    const response: any = await Helper.getTestcaseById(space, applicationid, requirementid, usecaseid, testcaseid);
    res.status(200);
    res.send(response);
    res.end();
};

export const updateTestcaseById = async(req:any, res:any)=>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const usecaseid = parameters[7];
    const testcaseid = parameters[9];
    const space = parameters[1];
    const response: any = await Helper.updateTestcaseById(space, applicationid, requirementid, usecaseid, testcaseid, req.body);
    res.status(200);
    res.send(response);
    res.end();
};

export const generateTestcase = async(req:any, res:any)=>{
    const parameters = req.url.split('/');
    const applicationid = parameters[3];
    const requirementid = parameters[5];
    const usecaseid = parameters[7];
    // const testcaseid = parameters[9];
    const space = parameters[1];
    const response: any = await Helper.generateTestcase(space, applicationid, requirementid, usecaseid);
    res.status(200);
    res.send(response);
    res.end();
}
