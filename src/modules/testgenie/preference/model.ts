var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const preferenceSchema = new Schema(
  {
    aiModel: { type: String, required:true },
  },
  {  timestamps: { createdAt: 'createdDate', updatedAt: 'lastModifiedDate' } }
);

const preferenceCollection = "preference";
export { preferenceSchema, preferenceCollection };
