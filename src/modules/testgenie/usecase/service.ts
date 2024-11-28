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
