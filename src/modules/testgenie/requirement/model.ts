
var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const requirementSchema = new Schema(
  {
    // appId: { type: String, required: true },
    // name: { type: String, required: true }, 
    description: { type: String },
    // status: { type: String, default: 'open' }, 
  },
  {  timestamps: { createdAt: 'createdDate', updatedAt: 'lastModifiedDate' } }
);

const requirementCollection = "requirement";
export { requirementSchema, requirementCollection };
