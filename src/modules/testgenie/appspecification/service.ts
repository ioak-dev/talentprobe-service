import * as Helper from "./helper";


export const getAppUseCases = async (req: any, res: any) => {
    const response: any = await Helper.getAppUseCases(req.params.id);
    res.status(response ? 200 : 404);
    res.send(response);
    res.end();
};

export const createAppUseCase = async (req: any, res: any) => {
    const response: any = await Helper.createAppUseCase(req.params.id,req.body);
    res.status(200);
    res.send(response);
    res.end();
};

export const createAppTestCase = async (req: any, res: any) => {
    const response: any = await Helper.createAppTestCase(req.params.id,req.params.usecaseid);
    res.status(200);
    res.send(response);
    res.end();
};


