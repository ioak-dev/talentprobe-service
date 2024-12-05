import * as Helper from "./helper";
import * as userInviteService from "../user/invite/service";
import * as PermissionHelper from "../permission/helper";

const selfRealm = 100;

export const createCompany = async(req: any, res:any) => {
  const userId = req.user.user_id;
  const data = req.body;
  const company: any = await Helper.createCompany(data, userId);
  userInviteService.registerUserInvite(
    company._doc.reference,
    company._doc._id,
    userId,
    req.user.email
  );
  await PermissionHelper.addRole(req.user.email, company._doc.reference);
  res.status(200);
  res.send(company);
  res.end();
}

export const updateCompany = async (req: any, res: any) => {
  const id = req.url.split('/')[2];
  req.body._id = id;
  const company: any = await Helper.updateCompany(req.body);
  if (!company) { 
    return res.status(401).send("Unauthorized: ID not found");
  }
  res.status(200);
  res.send(company);
  res.end();
};

export const getCompany = async (req: any, res: any) => {
  const userId = req.user.user_id;
  const companyList: any = await Helper.getCompany();
  res.status(200);
  res.send(companyList);
  res.end();
};

export const getCompanyByReference = async (reference: string) => {
  return await Helper.getCompanyByReference(reference);
};
