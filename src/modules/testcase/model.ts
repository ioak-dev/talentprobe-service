var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const testCaseSchema = new Schema(
  {
    suiteId: { type: String },
    useCaseId: { type: String },
    description: { type: JSON },
    serializedDescription: { type: String },
    summary: { type: String },
    priority: { type: String },
    comments: { type: String },
    components: { type: String },
    labels: { type: String },
  },
  { timestamps: true }
);

const testCaseCollection = "testcase";
export { testCaseSchema, testCaseCollection };