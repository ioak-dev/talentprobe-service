import * as Helper from "./helper";

const DATE_FORMAT = 'yyyyMMdd_HHmmss';
const HEADER_NAME = 'Content-Disposition';
const HEADER_VALUES = 'attachment; filename=';

export const getAllSuite = async (req: any, res: any) => {
//  const userId = req.user.user_id;
  const response: any[] = await Helper.getAllSuite();
  res.status(200);
  res.send(response);
  res.end();
};



export const updateSuiteById = async (req: any, res: any) => {
  const response: any = await Helper.updateSuiteById(
    req.params.id,
    req.body
  );
  res.status(200);
  res.send(response);
  res.end();
};

export const createSuite = async (req: any, res: any) => {
  const response: any = await Helper.createSuite(req.body);
  res.status(200);
  res.send(response);
  res.end();
};


export const exportSuite = async (req: any, res: any) => {
  try {
    //const { suiteId, type } = req.params;
    const suiteId= "667ba7051e1dfa57e0b97af6";
    const type="CSV";
    console.log(suiteId, type);
    const result = await Helper.exportSuite(suiteId, type);

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

export const deleteSuiteById = async (req: any, res: any) => {
  const response: any = await Helper.deleteSuiteById(
    req.params.id
  );
  res.status(200);
  res.send(response);
  res.end();
};


export const getSuiteById = async (req: any, res: any) => {
  const response: any = await Helper.getSuiteById(
    req.params.id
  );
  res.status(200);
  res.send(response);
  res.end();
};