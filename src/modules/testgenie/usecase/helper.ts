import { getCollection } from "../../../lib/dbutils";
import { getUseCaseGenPrompt } from "./prompt";
import { requirementCollection, requirementSchema } from "../requirement/model";
import { usecaseCollection, usecaseSchema } from "./model";
import { Gemini } from "aihub";
const {GEMINI_API_KEY} = require("../../../../env.js");

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
    const data = await model.findOne({ applicationId: applicationid, _id: requirementid });
    const response = await Gemini.process(
        GEMINI_API_KEY, "/v1beta/models/gemini-1.5-flash:generateContent",
        getUseCaseGenPrompt(data.description),
        "list"
    )

    const response_list = response.responseList;
    const usecaseModel = getCollection(
        space,
        usecaseCollection,
        usecaseSchema
    )
    response_list.forEach((usecase: any) => {
        const body = { applicationId: applicationid, requirementId: requirementid, overview: usecase.overview, label: usecase.label, description: usecase.description };
        usecaseModel.create(body);
    });

    return response_list;
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
    const result = await model.deleteOne({ applicationId: applicationid, requirementId: requirementid, _id: usecaseid});
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
