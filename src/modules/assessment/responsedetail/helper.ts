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
import { response } from "express";
import {
  finalizeResponse,
  getResponseHeaderById,
  updateScore,
} from "../responseheader/helper";

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

export const getAssessmentResponsedetailByResponseId= async (responseId: string) => {
  const model = getGlobalCollection(
    assessmentResponsedetailCollection,
    assessmentResponsedetailSchema
  );

  return await model.find({ responseId });
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

export const importQuestions = async (responseId: string, questions: any[]) => {
  const model = getGlobalCollection(
    assessmentResponsedetailCollection,
    assessmentResponsedetailSchema
  );

  const _payload: any[] = [];
  questions.forEach((question: any) =>
    _payload.push({
      insertOne: {
        document: {
          responseId,
          question,
          answer: null,
          isSubmitted: false,
          score: 0,
        },
      },
    })
  );
  await model.bulkWrite(_payload);
};

export const getNextQuestion = async (responseId: string) => {
  const model = getGlobalCollection(
    assessmentResponsedetailCollection,
    assessmentResponsedetailSchema
  );

  const response = await model.aggregate([
    { $match: { responseId: responseId.toString(), isSubmitted: false } },
    { $sample: { size: 1 } },
  ]);

  if (response.length > 0) {
    return response[0];
  }

  return null;
};

export const createAssessmentResponsedetail = async (
  id: string,
  responseId: string,
  data: any
) => {
  const responseHeader = await getResponseHeaderById(responseId);
  if (!responseHeader) {
    return {
      error: "Invalid response id"
    }
  }
  const model = getGlobalCollection(
    assessmentResponsedetailCollection,
    assessmentResponsedetailSchema
  );

  const responseDetail = await model.findOne({
    _id: data.referenceId,
  });
  if (!responseDetail) {
    return {
      error: "Invalid reference id"
    }
  }
  const score = responseDetail.question.data.answer === data.answer ? 1 : 0;
  const response = await model.findOneAndUpdate(
    { _id: data.referenceId, isSubmitted: false },
    {
      answer: data.answer,
      isSubmitted: true,
      score,
    },
    { returnOriginal: false }
  );

  if (response) {
    await updateScore(responseId, score);
  }

  const responseHeaderAfterUpdate = await getResponseHeaderById(responseId);
  const nextQuestion = await getNextQuestion(responseId);

  if (nextQuestion) {
    return {
      hasSubmitted: false,
      question: {
        questionId: nextQuestion.question._id,
        question: nextQuestion.question.data.question,
        choices: nextQuestion.question.data.choices,
        type: nextQuestion.question.type,
      },
      responseId,
      referenceId: nextQuestion._id,
      currentQuestionNumber: responseHeaderAfterUpdate.answered + 1,
      totalQuestions: responseHeaderAfterUpdate.totalQuestions,
    };
  }

  await finalizeResponse(responseId);

  return {
    hasSubmitted: true,
    responseId,
    totalQuestions: responseHeaderAfterUpdate.totalQuestions,
  };
};
