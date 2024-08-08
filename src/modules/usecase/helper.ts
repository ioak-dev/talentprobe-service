import { getGlobalCollection } from "../../lib/dbutils";
import {
  useCaseCollection,
  useCasechema,
} from "./model";

import * as Gptutils from "../../lib/gptutils";
import { getTestCaseGenPrompt } from "./prompt";
import { testCaseCollection, testCaseSchema } from "../testcase/model";

export const getAllUseCase = async () => {
  const model = getGlobalCollection(
    useCaseCollection,
    useCasechema
  );
  return await model.find({});
};


export const createUseCase = async (data: any) => {
  const model = getGlobalCollection(
    useCaseCollection,
    useCasechema
  );
  const response= await Gptutils.predict(getTestCaseGenPrompt(data))
  const testCaseModel = getGlobalCollection(
    testCaseCollection,
    testCaseSchema
  ); 

  await testCaseModel.create(response);
  return await model.create(data);
};

export const deleteUseCaseById = async (
    suiteId:string,
    id: string,
) => {
  const model = getGlobalCollection(
    useCaseCollection,
    useCasechema
  );
  return await model.deleteMany({ _id: id, suiteId:suiteId });
};


export const getUseCaseById = async (
suiteId:string,
  id: string,
) => {
  const model = getGlobalCollection(
    useCaseCollection,
    useCasechema
  );
  return await model.find({ _id: id , suiteId:suiteId});
};


export const updateUseCaseById = async (
  id: string,
  suiteId: string,
  data: any,
) => {
  const model = getGlobalCollection(
    useCaseCollection,
    useCasechema
  );
  const _payload: any[] = [];
  _payload.push({
    updateOne: {
      filter: {
        // _id: item._id,
        _id: id,
        suiteId: suiteId
      },
      update: {
        ...data,
      },
      upsert: true,
    },
  });
  const response= await Gptutils.predict(getTestCaseGenPrompt(data))
  const testCaseModel = getGlobalCollection(
    testCaseCollection,
    testCaseSchema
  ); 

  await testCaseModel.deleteMany({suiteId: suiteId, usecaseId: id })
  await testCaseModel.create(response);
  return await model.bulkWrite(_payload);
};

