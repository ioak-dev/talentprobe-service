
const axios = require("axios");
import { getGlobalCollection } from "../../../lib/dbutils";
import {
  testcaseCollection,
  testcaseSchema,
} from "./model";


export const getAllTestCases = async (suiteId: string,usecaseId: string) => {
  const model = getGlobalCollection(testcaseCollection, testcaseSchema);

  return await model.find({ suiteId: suiteId, useCaseId: usecaseId });
};
