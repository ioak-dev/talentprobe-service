  import {usecaseCollection, usecaseSchema} from "../usecase/model";

  const axios = require("axios");
  import { getGlobalCollection } from "../../../lib/dbutils";
  import {
    applicationTestcaseCollection,
    applicationTestcaseSchema,
  } from "./model";
  import {response} from "express";
  import * as Gptutils from "../../../lib/gptutils";
  import {getTestGenieApplicationPrompt} from "./prompt";


  export const generateConsolidatedTestCases = async (suiteId: string) => {
    const useCaseModel = getGlobalCollection(usecaseCollection,usecaseSchema);
    const model = getGlobalCollection(applicationTestcaseCollection, applicationTestcaseSchema);
    const useCaseList = await useCaseModel.find({suiteId: suiteId});

    if(useCaseList.length === 0){
      return response.status(404).send("No use case found for the suite id");
    }
    else {
      let constructUseCaseDescription = "";
      useCaseList.forEach((item: any) => {
        constructUseCaseDescription += item.description+"\n";
      });
      model.deleteMany({ suiteId: suiteId });
      const gptResponseList = await Gptutils.predict(getTestGenieApplicationPrompt(constructUseCaseDescription.toString().trim()));
      const _testCasesPayload: any[] = [];
        for (const item of gptResponseList?.testCases) {
          _testCasesPayload.push({
            insertOne: {
              document: {suiteId: suiteId,
                description: {
                  overview: item.description.overview,
                  steps: item.description.steps,
                  expectedOutcome: item.description.expectedOutcome
                },
                summary: item.summary,
                priority: item.priority,
                comments: item.comments,
                components: item.components,
                labels: item.labels,
                serializedDescription: getSerializedDescription(item)
              },
            },
          });
        }
        return model.bulkWrite(_testCasesPayload);
    }
  };

  const getSerializedDescription = (item: any):string => {
    const descriptionMap:{[key:string]:string} = {
      "Overview":item.description?.overview,
      "Test steps": item.description.steps.join("\n"),
      "Expected outcome":item.description?.expectedOutcome,  };
    let descriptionBuilder = "";
    for(const[key,value] of Object.entries(descriptionMap)){
      if (value){
        descriptionBuilder += `${key} : ${value}\n`;
      }
    }
    return descriptionBuilder.trim();
  };

  export const getAllConsolidatedTestCase = async (suiteId: string) => {
    const model = getGlobalCollection(applicationTestcaseCollection, applicationTestcaseSchema);
    const response = await model.find({ suiteId: suiteId });
    if (response.length > 0) {
      return response[0];
    }
    return null;
  };
