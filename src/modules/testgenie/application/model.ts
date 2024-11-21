var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const applicationSchema = new Schema(
  {
    name: { type: String, required:true },
  },
  {  timestamps: { createdAt: 'createdDate', updatedAt: 'lastModifiedDate' } }
);

const applicationCollection = "application";
export { applicationSchema, applicationCollection };
