var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const suiteSchema = new Schema(
  {
    name: { type: String },
  },
  {  timestamps: { createdAt: 'createdDate', updatedAt: 'lastModifiedDate' } }
);

const suiteCollection = "suite";
export { suiteSchema, suiteCollection };
