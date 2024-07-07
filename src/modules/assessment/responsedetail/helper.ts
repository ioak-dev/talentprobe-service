const axios = require("axios");
// const ONEAUTH_API = process.env.ONEAUTH_API || "http://localhost:4010/api";
const ONEAUTH_API = process.env.ONEAUTH_API || "https://api.ioak.io:8010/api";
import { getGlobalCollection } from "../../../lib/dbutils";
import {
  assessmentResponsedetailCollection,
  assessmentResponsedetailSchema,
} from "./model";
const { getCollection } = require("../../../lib/dbutils");
import * as Gptutils from "../../../lib/gptutils";

export const updateAssessmentResponsedetail = async (
  id: string,
  questionId: string,
  data: any,
  userId: string
) => {
  const model = getGlobalCollection(
    assessmentResponsedetailCollection,
    assessmentResponsedetailSchema
  );
  const _payload: any[] = [];
  _payload.push({
    updateOne: {
      filter: {
        // _id: item._id,
        assessmentId: id,
        _id: questionId,
      },
      update: {
        ...data,
      },
      upsert: true,
    },
  });
  return await model.bulkWrite(_payload);
};

export const getAssessmentResponsedetail = async (id: string) => {
  const model = getGlobalCollection(
    assessmentResponsedetailCollection,
    assessmentResponsedetailSchema
  );

  return await model.find({ assessmentId: id });
};

export const createAssessmentResponsedetail = async (data: any) => {
  const model = getGlobalCollection(
    assessmentResponsedetailCollection,
    assessmentResponsedetailSchema
  );

  return await model.create(data);
};

export const deleteAssessmentResponsedetail = async (
  id: string,
  questionId: string
) => {
  const model = getGlobalCollection(
    assessmentResponsedetailCollection,
    assessmentResponsedetailSchema
  );
  return await model.deleteMany({ _id: questionId, assessmentId: id });
};
