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
  return await model.find({appId});
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
  return await model.deleteMany({appId});
}

export const deleteRequirementById = async (
  space: string,
  id: string,
) => {
  const model = getCollection(
    space,
    requirementCollection,
    requirementSchema
  );
  return await model.deleteOne({ _id: id });
};

export const getRequirementById = async (
  space:string,
  id: string,
) => {
  const model = getCollection(
    space,
    requirementCollection,
    requirementSchema
  );
  const response = await model.find({ _id: id });
  if (response.length > 0) {
    return response[0];
  }
  return null;
};

export const updateRequirementById = async (
  space:string,
  id: string,
  data: any,
) => {
  const model = getCollection(
    space,
    requirementCollection,
    requirementSchema
  );
  const updatedDocument = await model.findOneAndUpdate(
    { _id: id }, 
    { $set: data }, 
    {
      new: true, 
      upsert: true, 
    },
  );
  return updatedDocument;
};