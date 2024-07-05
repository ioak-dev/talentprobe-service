const axios = require("axios");
// const ONEAUTH_API = process.env.ONEAUTH_API || "http://localhost:4010/api";
const ONEAUTH_API = process.env.ONEAUTH_API || "https://api.ioak.io:8010/api";
import { getGlobalCollection } from "../../../lib/dbutils";
import {
  assessmentQuestionCollection,
  assessmentQuestionSchema,
} from "./model";
const { getCollection } = require("../../../lib/dbutils");
import * as Gptutils from "../../../lib/gptutils";

export const updateAssessmentQuestion = async (
  id: string,
  questionId: string,
  data: any,
  userId: string
) => {
  const model = getGlobalCollection(
    assessmentQuestionCollection,
    assessmentQuestionSchema
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

export const getAssessmentQuestion = async (id: string) => {
  const model = getGlobalCollection(
    assessmentQuestionCollection,
    assessmentQuestionSchema
  );

  return await model.find({ assessmentId: id });
};

export const createAssessmentQuestion = async (data: any) => {
  const model = getGlobalCollection(
    assessmentQuestionCollection,
    assessmentQuestionSchema
  );

  return await model.create(data);
};

export const deleteAssessmentQuestion = async (
  id: string,
  questionId: string
) => {
  const model = getGlobalCollection(
    assessmentQuestionCollection,
    assessmentQuestionSchema
  );
  return await model.deleteMany({ _id: questionId, assessmentId: id });
};

export const generateQuestions = async (
  id: string,
  count: number,
  text: any
) => {
  await Gptutils.predict(count, text);
  const model = getGlobalCollection(
    assessmentQuestionCollection,
    assessmentQuestionSchema
  );

  return {};

  // return await model.create(data);
};
