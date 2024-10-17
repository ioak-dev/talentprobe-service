var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const applicationTestcaseSchema = new Schema(
  {
    suiteId: { type: String },
    description : {type : JSON},
    serializedDescription: { type: String },
    summary: { type: String },
    priority: { type: String },
    comments: { type: String },
    components: { type: String },
    labels: { type: String },
  },
  {  timestamps: { createdAt: 'createdDate', updatedAt: 'lastModifiedDate' } }
);

const applicationTestcaseCollection = "application.testcase";
export { applicationTestcaseSchema, applicationTestcaseCollection };
