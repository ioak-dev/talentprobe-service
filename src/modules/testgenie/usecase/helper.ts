import * as Geminiutils from "../../../lib/geminiutils";

const axios = require("axios");
import { getCollection } from "../../../lib/dbutils";
import {getTestCaseGenPrompt} from "./prompt";
import { requirementCollection, requirementSchema } from "../requirement/model";

export const generateUsecase = async(
    space:string,
    id:string,
)=>{
    const model = getCollection(
        space,
        requirementCollection,
        requirementSchema
    );
    const data = await model.findOne({ _id: id});
    const response = await Geminiutils.predictGemini(getTestCaseGenPrompt(data.description));
    return response;
}
