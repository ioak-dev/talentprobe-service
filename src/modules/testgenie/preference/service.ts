import * as Helper from "./helper";

export const getPreference = async (req: any, res: any) => {
    const space = req.params.space;
    const response = await Helper.getPreference(space);
    res.status(200);
    res.send(response);
    res.end();
};

export const addPreference = async (req: any, res: any) => {
    const space = req.params.space;
    const response: any = await Helper.addPreference(space, req.body);
    res.status(200);
    res.send(response);
    res.end();
};