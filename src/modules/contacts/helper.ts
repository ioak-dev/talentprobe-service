const axios = require("axios");
// const ONEAUTH_API = process.env.ONEAUTH_API || "http://localhost:4010/api";
const ONEAUTH_API = process.env.ONEAUTH_API || "https://api.ioak.io:8010/api";
import { getGlobalCollection } from "../../lib/dbutils";
import { assessmentCollection, assessmentSchema } from "./model";
const { getCollection } = require("../../lib/dbutils");

export const updateAssessment = async (
  id: string,
  data: any,
  userId: string
) => {
  const model = getGlobalCollection(assessmentCollection, assessmentSchema);
  const _payload: any[] = [];
  _payload.push({
    updateOne: {
      filter: {
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

export const getAssessment = async () => {
  const model = getGlobalCollection(assessmentCollection, assessmentSchema);

  return await model.find({});
};

export const getAssessmentById = async (id: string) => {
  const model = getGlobalCollection(assessmentCollection, assessmentSchema);

  const response = await model.find({ _id: id });
  if (response.length > 0) {
    return response[0];
  }

  return null;
};

export const createAssessment = async (data: any) => {
  const model = getGlobalCollection(assessmentCollection, assessmentSchema);

  return await model.create(data);
};

export const deleteByTransactionId = async (
  space: string,
  transactionId: string
) => {
  const model = getCollection(space, assessmentCollection, assessmentSchema);
  return await model.deleteMany({ transactionId });
};
