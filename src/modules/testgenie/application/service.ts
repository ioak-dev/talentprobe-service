import * as Helper from "./helper";

const DATE_FORMAT = 'yyyyMMdd_HHmmss';
const HEADER_NAME = 'Content-Disposition';
const HEADER_VALUES = 'attachment; filename=';

export const getAllApp = async (req: any, res: any) => {
  //const userId = req.user.user_id;
  const space = req.params.space;
  const response = await Helper.getAllApp(space);
  res.status(200);
  res.send(response);
  res.end();
};

// export const getAllApp = async (req: any, res: any) => {
//   try {
//     const space = req.params.space; 
//     const response = await Helper.getAllApp(space);
//     res.status(200).json(response);
//   } catch (error) {
//     console.error("Error fetching applications:", error);
//     res.status(500).json({ error: "Failed to fetch applications" });
//   }
// };


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


export const exportApp = async (req: any, res: any) => {
  try {
    //const { suiteId, type } = req.params;
    const appId= "667ba7051e1dfa57e0b97af6";
    const type="CSV";
    console.log(appId, type);
    const result = await Helper.exportApp(appId, type);

    if ('body' in result) {
      res.status(200).json(result.body);
    } else {
      res.set({
        [HEADER_NAME]: HEADER_VALUES + result.fileName,
        'Content-Length': result.fileContentLength,
        'Content-Type': result.contentType,
      });
      result.resource.pipe(res);
    }
  } catch (error:any) {
    res.status(500).send(error.message);
  }
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