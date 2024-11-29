import { getCollection } from "../../../lib/dbutils";
import {
  requirementCollection,
  requirementSchema,
} from "./model";
    
export const getRequirementByApp = async (space:string, appId: string) => {
  const model = getCollection(
    space,
    requirementCollection,
    requirementSchema
  );
  return await model.find({applicationId: appId});
};

export const createRequirement = async (space: string, data: any) => {
  const model = getCollection(
    space,
    requirementCollection,
    requirementSchema
  );

  if(!data.description){
    throw new Error("Description field is required");
  }
  const createdApp = await model.create(data)
  return createdApp;
};

export const deleteRequirement = async(space: string, appId:string) => {
  const model = getCollection(
    space,
    requirementCollection,
    requirementSchema
  );
  return await model.deleteMany({applicationId: appId});
}

export const deleteRequirementById = async (
  space: string,
  applicationid: string,
  requirementid: string
) => {
  const model = getCollection(
    space,
    requirementCollection,
    requirementSchema
  );
  return await model.deleteOne({ applicationId: applicationid , _id: requirementid });
};

export const getRequirementById = async (
  space:string,
  applicationid: string,
  requirementid: string
) => {
  const model = getCollection(
    space,
    requirementCollection,
    requirementSchema
  );
  const response = await model.find({applicationId:applicationid, _id: requirementid });
  if (response.length > 0) {
    return response[0];
  }
  return null;
};

export const updateRequirementById = async (
  space:string,
  applicationid: string,
  requirementid: string,
  data: any,
) => {
  const model = getCollection(
    space,
    requirementCollection,
    requirementSchema
  );
  const updatedDocument = await model.findOneAndUpdate(
    { applicationId: applicationid , _id: requirementid }, 
    { $set: data }, 
    {
      new: true, 
      upsert: false, 
    },
  );
  return updatedDocument;
};