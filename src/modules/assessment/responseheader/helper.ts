const axios = require("axios");
// const ONEAUTH_API = process.env.ONEAUTH_API || "http://localhost:4010/api";
const ONEAUTH_API = process.env.ONEAUTH_API || "https://api.ioak.io:8010/api";
import { getGlobalCollection } from "../../../lib/dbutils";
import {
  assessmentResponseheaderCollection,
  assessmentResponseheaderSchema,
} from "./model";
const { getCollection } = require("../../../lib/dbutils");
import * as Gptutils from "../../../lib/gptutils";

export const updateAssessmentResponseheader = async (
  id: string,
  questionId: string,
  data: any,
  userId: string
) => {
  const model = getGlobalCollection(
    assessmentResponseheaderCollection,
    assessmentResponseheaderSchema
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

export const getAssessmentResponseheader = async (id: string) => {
  const model = getGlobalCollection(
    assessmentResponseheaderCollection,
    assessmentResponseheaderSchema
  );

  return await model.find({ assessmentId: id });
};

export const createAssessmentResponseheader = async (data: any) => {
  const model = getGlobalCollection(
    assessmentResponseheaderCollection,
    assessmentResponseheaderSchema
  );

  return await model.create(data);
};

export const deleteAssessmentResponseheader = async (
  id: string,
  questionId: string
) => {
  const model = getGlobalCollection(
    assessmentResponseheaderCollection,
    assessmentResponseheaderSchema
  );
  return await model.deleteMany({ _id: questionId, assessmentId: id });
};
