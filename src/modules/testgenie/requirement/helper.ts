import { getCollection } from "../../../lib/dbutils";
import fs from 'fs-extra';
import path from 'path';
import { format } from 'date-fns';
import { parseAsync } from 'json2csv';
import { Readable } from 'stream';
import {
  requirementCollection,
  requirementSchema,
} from "./model";
import { testcaseCollection, testcaseSchema } from "../testcase/model";
const DATE_FORMAT = 'yyyyMMdd_HHmmss';

const space = "dummy";
    
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
  // console.log("Data to save: ", data);

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
  // appId:string,
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



export async function exportApp(suiteId: string, type: string) {
  try {
    const tempDirPath = path.join(__dirname, 'temp');
    await fs.ensureDir(tempDirPath);
    const timestamp = format(new Date(), DATE_FORMAT);
    const fileName = `testcase_${timestamp}.csv`;
    const filePath = path.join(tempDirPath, fileName);

    console.log(tempDirPath)
    console.log(fileName)
    const model = getCollection( 
      space,
      testcaseCollection,
      testcaseSchema);

    const testCaseList = await model.find({});
    console.log(testCaseList)
    if (type === 'JSON') {

      return { contentType: 'application/json', body: testCaseList };
    }

    const suiteModel = getCollection(
      space,
      requirementCollection,
      requirementSchema
    );

    const suite = await suiteModel.findOne({ _id : suiteId });
    if (!suite) {
      throw new Error('Suite not found');
    }

    const csvHeaders = computeStaticHeaders();
    const rowData: string[][] = [];
    computeCsvRecords(testCaseList, suite.name, rowData);

    const csv = await parseAsync(rowData, { fields: csvHeaders });
    await fs.writeFile(filePath, csv);

    const fileContent = await fs.readFile(filePath);
    const resource = Readable.from(fileContent);

    return {
      contentType: 'text/csv',
      fileName,
      fileContentLength: fileContent.length,
      resource,
    };
  } catch (error: any) {
    throw new Error(error.message);
  }
}


function computeStaticHeaders() {
  return ['Name', 'Description', 'Summary', 'Priority', 'Comments', 'Components', 'Labels'];
}

function computeCsvRecords(testCaseList: any, suiteName: any, rowData: any) {
  testCaseList.forEach((testCase: any) => {
    const recordValue = [
      suiteName,
      buildDescriptionFromGptResponse(testCase.description),
      testCase.summary,
      testCase.priority,
      testCase.comments,
      testCase.components,
      testCase.labels
    ];
    rowData.push(recordValue);
  });
}

function buildDescriptionFromGptResponse(testDescriptionResource: any) {
  const descriptionMap = {
    'Overview': testDescriptionResource.overview,
    'Test steps': testDescriptionResource.steps.join('\n'),
    'Expected outcome': testDescriptionResource.expectedOutcome
  };

  return Object.entries(descriptionMap)
    .map(([key, value]) => `${key}: ${value}`)
    .join('\n\n')
    .trim();
}