import { getGlobalCollection } from "../../lib/dbutils";
import fs from 'fs-extra';
import path from 'path';
import { format } from 'date-fns';
import { parseAsync } from 'json2csv';
import { Readable } from 'stream';
import {
  suiteCollection,
  suiteSchema,
} from "./model";
import { model } from "mongoose";
import { testCaseCollection, testCaseSchema } from "../testcase/model";
const { getCollection } = require("../../lib/dbutils");
const DATE_FORMAT = 'yyyyMMdd_HHmmss';
const HEADER_NAME = 'Content-Disposition';
const HEADER_VALUES = 'attachment; filename=';


export const getAllSuite = async () => {
  const model = getGlobalCollection(
    suiteCollection,
    suiteSchema
  );
  return await model.find({});
};


export const createSuite = async (data: any) => {
  const model = getGlobalCollection(
    suiteCollection,
    suiteSchema
  );

  return await model.create(data);
};

export const deleteSuiteById = async (
  id: string,
) => {
  const model = getGlobalCollection(
    suiteCollection,
    suiteSchema
  );
  return await model.deleteMany({ _id: id });
};


export const getSuiteById = async (
  id: string,
) => {
  const model = getGlobalCollection(
    suiteCollection,
    suiteSchema
  );
  return await model.find({ _id: id });
};


export const updateSuiteById = async (
  id: string,
  data: any,
) => {
  const model = getGlobalCollection(
    suiteCollection,
    suiteSchema
  );
  const _payload: any[] = [];
  _payload.push({
    updateOne: {
      filter: {
        // _id: item._id,
        _id: id,
      },
      update: {
        ...data,
      },
      upsert: true,
    },
  });
  return await model.bulkWrite(_payload);
};



export async function exportSuite(suiteId: string, type: string) {
  try {
    const tempDirPath = path.join(__dirname, 'temp');
    await fs.ensureDir(tempDirPath);
    const timestamp = format(new Date(), DATE_FORMAT);
    const fileName = `testcase_${timestamp}.csv`;
    const filePath = path.join(tempDirPath, fileName);

    console.log(tempDirPath)
    console.log(fileName)
    const model = getGlobalCollection(
      testCaseCollection,
      testCaseSchema
    );

    const testCaseList = await model.find({});
    console.log(testCaseList)
    if (type === 'JSON') {

      return { contentType: 'application/json', body: testCaseList };
    }

    const suiteModel = getGlobalCollection(
      suiteCollection,
      suiteSchema
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