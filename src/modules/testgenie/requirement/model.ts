
var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const requirementSchema = new Schema(
  {
    applicationId: {type: String},
    description: { type: String }, 
  },
  {  timestamps: { createdAt: 'createdDate', updatedAt: 'lastModifiedDate' } }
);

const requirementCollection = "requirement";
export { requirementSchema, requirementCollection };
