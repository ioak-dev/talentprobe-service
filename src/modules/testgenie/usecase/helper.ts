import * as Geminiutils from "../../../lib/geminiutils";

const axios = require("axios");
import { getCollection } from "../../../lib/dbutils";
import { getTestCaseGenPrompt } from "./prompt";
import { requirementCollection, requirementSchema } from "../requirement/model";
import { usecaseCollection, usecaseSchema } from "./model";

export const generateUsecase = async (
    space: string,
    requirementid: string,
    applicationid: string,
) => {
    const model = getCollection(
        space,
        requirementCollection,
        requirementSchema
    );
    const data = await model.findOne({ _id: requirementid });
    const response = await Geminiutils.predictGemini(getTestCaseGenPrompt(data.description));

    const usecaseModel = getCollection(
        space,
        usecaseCollection,
        usecaseSchema
    )
    response.forEach((usecase: any) => {
        const body = {applicationId:applicationid, requirementId:requirementid, overview:usecase.overview, label:usecase.label, description:usecase.description };
        usecaseModel.create(body);
    });

    return response;
}
