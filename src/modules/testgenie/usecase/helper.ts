import * as Geminiutils from "../../../lib/geminiutils";

const axios = require("axios");
import { getCollection } from "../../../lib/dbutils";
import { getTestCaseGenPrompt } from "./prompt";
import { requirementCollection, requirementSchema } from "../requirement/model";
import { usecaseCollection, usecaseSchema } from "./model";

export const generateUsecase = async (
    space: string,
    id: string,
) => {
    const model = getCollection(
        space,
        requirementCollection,
        requirementSchema
    );
    const data = await model.findOne({ _id: id });
    const response = await Geminiutils.predictGemini(getTestCaseGenPrompt(data.description));

    const usecaseModel = getCollection(
        space,
        usecaseCollection,
        usecaseSchema
    )

    const body = {requirementId:id, usecases:response};
    await usecaseModel.create(body);
    return response;
}
