import { getCollection } from "../../../lib/dbutils";
import {
  applicationCollection,
  applicationSchema,
} from "./model";
    
export const getAllApp = async (space:string) => {
  const model = getCollection(
    space,
    applicationCollection,
    applicationSchema
  );
  return await model.find({});
};

export const createApp = async (space: string, data: any) => {
  const model = getCollection(
    space,
    applicationCollection,
    applicationSchema
  );
  const createdApp = await model.create(data)
  return createdApp;
};

export const deleteAppById = async (
  space: string,
  id: string,
) => {
  const model = getCollection(
    space,
    applicationCollection,
    applicationSchema
  );
  return await model.deleteOne({ _id: id });
};

export const getAppById = async (
  space:string,
  id: string,
) => {
  const model = getCollection(
    space,
    applicationCollection,
    applicationSchema
  );
  const response = await model.find({ _id: id });
  if (response.length > 0) {
    return response[0];
  }
  return null;
};

export const updateAppById = async (
  space:string,
  id: string,
  data: any,
) => {
  const model = getCollection(
    space,
    applicationCollection,
    applicationSchema
  );
  const updatedDocument = await model.findOneAndUpdate(
    { _id: id }, 
    { $set: data },
    {
      new: true, 
      upsert: false, 
    },
  );
  return updatedDocument;
};
