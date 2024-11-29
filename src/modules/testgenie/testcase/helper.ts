import { getCollection } from "../../../lib/dbutils";
import { testcaseCollection, testcaseSchema } from "./model";

export const createTestcase = async(
    space: string,
    applicationId: string,
    requirementId: string,
    usecaseId: string,
    data: any
)=>{
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

export const getTestcase = async(
    space:string,
    applicationId: string,
    requirementId: string,
    usecaseId: string
)=>{
    const model = getCollection(
        space,
        testcaseCollection,
        testcaseSchema
    );
    const result = await model.find({applicationId: applicationId, requirementId: requirementId, usecaseId: usecaseId});
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
    const result = await model.deleteOne({ applicationId: applicationid, requirementId: requirementid, usecaseId: usecaseid , _id: testcaseid});
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
        { applicationId: applicationid, requirementId: requirementid, usecaseId: usecaseid, _id:testcaseid },
        data,
        { upsert: false, new:true });
    return updated;
};
