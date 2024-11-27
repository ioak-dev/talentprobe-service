import * as Helper from "./helper";

export const generateUsecase = async(req:any, res:any) => {
    req.body.requirementId = req.params.id;
    const space = req.params.space;
    const response: any = await Helper.generateUsecase(space, req.params.id);
    res.status(200);
    res.send(response);
    res.end();
};
