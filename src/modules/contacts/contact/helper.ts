const axios = require("axios");
// const ONEAUTH_API = process.env.ONEAUTH_API || "http://localhost:4010/api";
const ONEAUTH_API = process.env.ONEAUTH_API || "https://api.ioak.io:8010/api";
import { getGlobalCollection } from "../../../lib/dbutils";
import { contactCollection, contactSchema } from "./model";
const { getCollection } = require("../../../lib/dbutils");

export const updateContact = async (id: string, data: any) => {
  const model = getGlobalCollection(contactCollection, contactSchema);
  const _payload: any[] = [];
  _payload.push({
    updateOne: {
      filter: {
        // _id: item._id,
        _id: id,
      },
      update: {
        ...data,
      },
      upsert: true,
    },
  });
  return await model.bulkWrite(_payload);
};

export const getContact = async () => {
  const model = getGlobalCollection(contactCollection, contactSchema);

  return await model.find({});
};

export const getContactById = async (id: string) => {
  const model = getGlobalCollection(contactCollection, contactSchema);

  return await model.findOne({ _id: id });
};

export const createContact = async (data: any) => {
  const model = getGlobalCollection(contactCollection, contactSchema);

  return await model.create(data);
};

export const deleteContact = async (id: string, questionId: string) => {
  const model = getGlobalCollection(contactCollection, contactSchema);
  return await model.deleteMany({ _id: questionId, assessmentId: id });
};
