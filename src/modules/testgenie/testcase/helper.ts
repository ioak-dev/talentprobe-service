import { Gemini } from "aihub";
import { getCollection } from "../../../lib/dbutils";
import { usecaseCollection, usecaseSchema } from "../usecase/model";
import { testcaseCollection, testcaseSchema } from "./model";
import { getTestCaseGenPrompt } from "./prompt";

const {GEMINI_API_KEY} = require("../../../../env.js");

export const createTestcase = async (
    space: string,
    applicationId: string,
    requirementId: string,
    usecaseId: string,
    data: any
) => {
    const model = getCollection(
        space,
        testcaseCollection,
        testcaseSchema
    );
    data.applicationId = applicationId;
    data.requirementId = requirementId;
    data.usecaseId = usecaseId;
    const created = await model.create(data);
    return created;
};

export const getTestcase = async (
    space: string,
    applicationId: string,
    requirementId: string,
    usecaseId: string
) => {
    const model = getCollection(
        space,
        testcaseCollection,
        testcaseSchema
    );
    const result = await model.find({ applicationId: applicationId, requirementId: requirementId, usecaseId: usecaseId });
    return result;
};

export const deleteAllTestcase = async (
    space: string,
    applicationid: string,
    requirementid: string,
    usecaseid: string
) => {
    const model = getCollection(
        space,
        testcaseCollection,
        testcaseSchema
    );
    const result = await model.deleteMany({ applicationId: applicationid, requirementId: requirementid, usecaseId: usecaseid });
    return result;
};

export const deleteTestcaseById = async (
    space: string,
    applicationid: string,
    requirementid: string,
    usecaseid: string,
    testcaseid: string
) => {
    const model = getCollection(
        space,
        testcaseCollection,
        testcaseSchema
    );
    const result = await model.deleteOne({ applicationId: applicationid, requirementId: requirementid, usecaseId: usecaseid, _id: testcaseid });
    return result;
};

export const getTestcaseById = async (
    space: string,
    applicationid: string,
    requirementid: string,
    usecaseid: string,
    testcaseid: string
) => {
    const model = getCollection(
        space,
        testcaseCollection,
        testcaseSchema
    );
    const result = model.find({ applicationId: applicationid, requirementId: requirementid, usecaseId: usecaseid, _id: testcaseid });
    return result;
};

export const updateTestcaseById = async (
    space: string,
    applicationid: string,
    requirementid: string,
    usecaseid: string,
    testcaseid: string,
    data: any
) => {
    const model = getCollection(
        space,
        testcaseCollection,
        testcaseSchema
    );

    const updated = await model.findOneAndUpdate(
        { applicationId: applicationid, requirementId: requirementid, usecaseId: usecaseid, _id: testcaseid },
        data,
        { upsert: false, new: true });
    return updated;
};

export const generateTestcase = async (
    space: string,
    applicationid: string,
    requirementid: string,
    usecaseid: string,
) => {
    const testcaseModel = getCollection(
        space,
        testcaseCollection,
        testcaseSchema
    );
    const usecaseModel = getCollection(
        space,
        usecaseCollection,
        usecaseSchema
    );
    const result: any = await usecaseModel.findOne({
        applicationId: applicationid,
        requirementId: requirementid,
        _id: usecaseid
    });
    if (result) {
        const response = await Gemini.process(
            GEMINI_API_KEY,
            "/v1beta/models/gemini-1.5-flash:generateContent",
            getTestCaseGenPrompt(result.description),
            "list"
        );
        const response_list = response.responseList;
        response_list.forEach((testcase: any) => {
            const body = {
                applicationId: applicationid,
                requirementId: requirementid,
                usecaseId: usecaseid,
                description: {
                    overview: testcase.description.overview,
                    steps: testcase.description.steps || [], 
                    expectedOutcome: testcase.description.expectedOutcome || "", 
                },
                summary: testcase.summary || "", 
                priority: testcase.priority || "", 
                comments: testcase.comments || "", 
                components: testcase.components || "", 
                label: testcase.label || "" 
            };
            testcaseModel.create(body);
        });
        
        return response_list;
    } else {
        console.log("No matching document found.");
    }

};