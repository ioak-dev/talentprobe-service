import { getGlobalCollection } from "../../lib/dbutils";
import {
  testCaseCollection,
  testCaseSchema,
} from "./model";
const { getCollection } = require("../../lib/dbutils");


export const getAllTestCases = async (suiteId:String,useCaseId:String) => {
    const model = getGlobalCollection(
      testCaseCollection,
      testCaseSchema
    );
    console.log(suiteId)
    console.log(useCaseId)
    return await model.find({suiteId:suiteId,useCaseId:useCaseId });
  };