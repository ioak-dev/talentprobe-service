const axios = require("axios");
// const ONEAUTH_API = process.env.ONEAUTH_API || "http://localhost:4010/api";
const ONEAUTH_API = process.env.ONEAUTH_API || "https://api.ioak.io:8010/api";
import { companyCollection, companySchema } from "./model";
import { getGlobalCollection, getCollection } from "../../lib/dbutils";
import { nextval } from "../sequence/service";
import {
  lastMonth,
  lastYear,
  thisMonth,
  thisYear,
} from "./ReservedFilterConfiguration";

export const updateCompany = async (data: any, userId: string) => {
  const model = getGlobalCollection(companyCollection, companySchema);
  if (data._id) {
    const response = await model.findByIdAndUpdate(
      data._id,
      {
        ...data,
      },
      { new: true, upsert: true }
    );
    return response;
  }

  const response = await model.create({
    ...data,
    reference: await nextval("companyId"),
  });

  return response;
};

export const getCompany = async () => {
  const model = getGlobalCollection(companyCollection, companySchema);

  return await model.find();
};

export const getCompanyByReference = async (reference: string) => {
  const model = getGlobalCollection(companyCollection, companySchema);

  return await model.findOne({ reference });
};

export const getCompanyByIdList = async (idList: string[]) => {
  const model = getGlobalCollection(companyCollection, companySchema);

  return await model.find({ _id: { $in: idList } });
};
