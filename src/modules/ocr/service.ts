import * as Helper from "./helper";

export const parseImage = async (req: any, res: any) => {
  if (!req.file) {
    return res.status(400).send("No file uploaded.");
  }

  const imageBuffer = req.file.buffer;
  const out: any = await Helper.parseImage(imageBuffer);
  res.status(200);
  res.send(out);
  res.end();
};
