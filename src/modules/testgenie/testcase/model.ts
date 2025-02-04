var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const testcaseSchema = new Schema(
  {
    applicationId: {type: String},
    requirementId: {type: String},
    usecaseId: {type:String},
    description: {
        overview: { type: String },
        steps: { type: [String] },
        expectedOutcome: { type: String }
      },
    summary: {type:String},
    priority: {type: String},
    comments: {type:String},
    components: {type: String},
    label: {type:String}
  },
  { timestamps: { createdAt: 'createdDate', updatedAt: 'lastModifiedDate' } }
);

const testcaseCollection = "testcase";

export { testcaseSchema, testcaseCollection };
