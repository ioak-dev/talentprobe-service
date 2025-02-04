import { getCollection } from "../../../lib/dbutils";
import { preferenceCollection, preferenceSchema } from "./model";

export const getPreference = async(space:string)=>{
    const model = getCollection(
        space,
        preferenceCollection,
        preferenceSchema
    );
    return await model.find({});
};

export const addPreference = async(space:string, data:any)=>{
    const model = getCollection(
        space,
        preferenceCollection,
        preferenceSchema
      );
      const createdPreference = await model.create(data)
      return createdPreference;
};