import * as Helper from "./helper";


export const getAllUseCases = async (req: any, res: any) => {
    const response: any = await Helper.getAllUseCases(req.params.id);
    res.status(response ? 200 : 404);
    res.send(response);
    res.end();
};

export const createUseCase = async (req: any, res: any) => {
    const response: any = await Helper.createUseCase(req.params.id,req.body);
    res.status(200);
    res.send(response);
    res.end();
};


export const updateUseCaseById = async (req: any, res: any) => {
    const response: any = await Helper.updateUseCaseById(
        req.params.id,
        req.params.usecaseid,
        req.body
    );
    res.status(200);
    res.send(response);
    res.end();
};

export const getUseCaseById = async (req: any, res: any) => {
    const response: any = await Helper.getUseCaseById(req.params.id,req.params.usecaseid);
    res.status(response ? 200 : 404);
    res.send(response);
    res.end();
};

export const deleteUseCaseById = async (req: any, res: any) => {
    const response: any = await Helper.deleteUseCaseById(req.params.id,req.params.usecaseid);
    res.status(response ? 200 : 404);
    res.send(response);
    res.end();
};


