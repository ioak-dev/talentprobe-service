import * as Gptutils from "../../../lib/gptutils";

const axios = require("axios");
import { getGlobalCollection } from "../../../lib/dbutils";
import {
    appusecaseSchema,
    appusecaseCollection
} from "./model";
import {getAppUseCasesPrompt} from "./prompt";
import {suiteCollection, suiteSchema} from "../suite/model";
import {testcaseCollection, testcaseSchema} from "../testcase/model";
import {getTestCaseGenPrompt} from "../usecase/prompt";
import {usecaseCollection, usecaseSchema} from "../usecase/model";


export const getAppUseCases = async (id: string) => {
    const model = getGlobalCollection(usecaseCollection, usecaseSchema);
    return await model.find({ suiteId: id });

};

export const createAppUseCase = async (id: string, data: any) => {
    const model = getGlobalCollection(usecaseCollection,usecaseSchema);
    const gptResponse= await Gptutils.predict(getAppUseCasesPrompt(data.description));
    const _useCasesPayload: any[] = [];
    gptResponse?.useCases?.forEach((item: any) =>
        _useCasesPayload.push({
            insertOne: {
                document: {suiteId: id,useCaseName:item.name, description:item.description, priority:item.priority, category:item.category},
            },
        })
    );
   return model.bulkWrite(_useCasesPayload);
};

export const createAppTestCase = async (id: string,usecaseid:string) => {
  const model = getGlobalCollection( usecaseCollection, usecaseSchema);
  const response = await model.find({ _id: usecaseid, suiteId: id });
  if (!response) {
    return "No usecase found";
  }
  const testCaseModel = getGlobalCollection(testcaseCollection, testcaseSchema);
  const gptResponse= await Gptutils.predict(getTestCaseGenPrompt(response[0].description));
  const _testCasesPayload: any[] = [];
  gptResponse?.testCases?.forEach((item: any) =>
      _testCasesPayload.push({
        insertOne: {
          document: {suiteId: id, useCaseId: usecaseid,description: { overview:item.description.overview, steps: item.description.steps, expectedOutcome: item.description.expectedOutcome },summary:item.summary ,priority:item.priority ,comments:item.comments ,components:item.components ,labels:item.labels},
        },
      })
  );
  return testCaseModel.bulkWrite(_testCasesPayload);
};