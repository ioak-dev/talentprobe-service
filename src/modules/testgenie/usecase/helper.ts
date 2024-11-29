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
        const body = { applicationId: applicationid, requirementId: requirementid, overview: usecase.overview, label: usecase.label, description: usecase.description };
        usecaseModel.create(body);
    });

    return response;
};

export const getUsecase = async (
    space: string,
    applicationid: string,
    requirementid: string
) => {
    const model = getCollection(
        space,
        usecaseCollection,
        usecaseSchema
    );
    const result = model.find({ applicationId: applicationid, requirementId: requirementid });
    return result;
};

export const createUsecase = async (
    space: string,
    applicationid: string,
    requirementid: string,
    data: any
) => {
    const model = getCollection(
        space,
        usecaseCollection,
        usecaseSchema
    );
    const body = { applicationId: applicationid, requirementId: requirementid, overview: data.overview, label: data.label, description: data.description };
    const created = await model.create(body);
    return created;
};

export const deleteAllUsecase = async (
    space: string,
    applicationid: string,
    requirementid: string
) => {
    const model = getCollection(
        space,
        usecaseCollection,
        usecaseSchema
    );
    const result = await model.deleteMany({ applicationId: applicationid, requirementId: requirementid });
    return result;
};

export const deleteUsecaseById = async (
    space: string,
    applicationid: string,
    requirementid: string,
    usecaseid: string
) => {
    const model = getCollection(
        space,
        usecaseCollection,
        usecaseSchema
    );
    const result = await model.deleteOne({ applicationId: applicationid, requirementId: requirementid, _id: usecaseid });
    return result;
};

export const getUsecaseById = async (
    space: string,
    applicationid: string,
    requirementid: string,
    usecaseid: string
) => {
    const model = getCollection(
        space,
        usecaseCollection,
        usecaseSchema
    );
    const result = model.find({ applicationId: applicationid, requirementId: requirementid, _id: usecaseid });
    return result;
};

export const updateUsecaseById = async (
    space: string,
    applicationid: string,
    requirementid: string,
    usecaseid: string,
    data: any
) => {
    const model = getCollection(
        space,
        usecaseCollection,
        usecaseSchema
    );

    const updated = await model.findOneAndUpdate(
        { applicationId: applicationid, requirementId: requirementid, _id: usecaseid },
        data,
        { upsert: false, new:true });
    return updated;
};
