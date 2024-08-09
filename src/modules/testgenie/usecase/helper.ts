import * as Gptutils from "../../../lib/gptutils";

const axios = require("axios");
import { getGlobalCollection } from "../../../lib/dbutils";
import {
    usecaseCollection,
    usecaseSchema,
} from "./model";
import {testcaseCollection, testcaseSchema} from "../testcase/model";
import {getTestCaseGenPrompt} from "./prompt";


export const getAllUseCases = async (id: string) => {
    const model = getGlobalCollection(usecaseCollection, usecaseSchema);
    const response = await model.find({ suiteId: id });
    if (response.length > 0) {
        return response[0];
    }
    return null;
};

export const createUseCase = async (id: string, data: any) => {
    const model = getGlobalCollection(usecaseCollection,usecaseSchema);
    const testCaseModel = getGlobalCollection(testcaseCollection,testcaseSchema);

    const response = await model.create(data);
    const gptResponse= await Gptutils.predict(getTestCaseGenPrompt(data))
    const _testCasesPayload: any[] = [];
    gptResponse?.testCases?.forEach((item: any) =>
        _testCasesPayload.push({
            insertOne: {
                document: {suiteId: id, useCaseId: response.id,description: { overview:item.description.overview, steps: item.description.steps, expectedOutcome: item.description.expectedOutcome },summary:item.summary ,priority:item.priority ,comments:item.comments ,components:item.components ,labels:item.labels},
            },
        })
    );
    return await testCaseModel.bulkWrite(_testCasesPayload);
};

/*export const updateUseCaseById = async (
    id: string,
    usecaseid: string,
    data: any
) => {
    const model = getGlobalCollection(
        usecaseCollection, usecaseSchema
    );
    const response = await model.find({ _id: usecaseid, suiteId: id });
    let usecase;
    if (response.length > 0) {
        usecase = response[0];
        const model = getGlobalCollection(testcaseCollection, testcaseSchema);
        usecase.useCaseName = data.useCaseName;
        usecase.description = data.description;
        model.deleteMany({ _id: usecaseid, suiteId: id });
        const gptResponse = await Gptutils.predict(getTestGeniePrompt(data.description));
console.log('test cases gpt call');
        const _testCasespayload: any[] = [];
        gptResponse?.testCases?.forEach((item: any) =>
            _testCasespayload.push({
                insertOne: {
                    document: {suiteId: id, useCaseId: usecaseid,description: { overview:item.description.overview, steps: item.description.steps, expectedOutcome: item.description.expectedOutcome },summary:item.summary ,priority:item.priority ,comments:item.comments ,components:item.components ,labels:item.labels},
                },
            })
        );
        await model.bulkWrite(_testCasespayload);
    }
    const _payload: any[] = [];
    _payload.push({
        updateOne: {
            filter: {
                suiteId: id,
                _id: usecaseid,
            },
            update: {
                usecase,
            },
            upsert: true,
        },
    });
    return await model.bulkWrite(_payload);
};*/

export const updateUseCaseById = async (
    id: string,
    suiteId: string,
    data: any,
) => {
    const model = getGlobalCollection(
        usecaseCollection,
        usecaseSchema
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
        testcaseCollection,
        testcaseSchema
    );

    await testCaseModel.deleteMany({suiteId: suiteId, usecaseId: id })
    await testCaseModel.create(response);
    return await model.bulkWrite(_payload);
};



export const getUseCaseById = async (id: string, usecaseid: string) => {
    const model = getGlobalCollection( usecaseCollection, usecaseSchema);
    const response = await model.find({ _id: usecaseid, suiteId: id });
    if (response.length > 0) {
        return response[0];
    }
    return null;
};


export const deleteUseCaseById = async (
    id: string,
    usecaseid: string
) => {
    const model = getGlobalCollection(
        usecaseCollection, usecaseSchema
    );
    return await model.deleteMany({ _id: usecaseid, suiteId: id });
};