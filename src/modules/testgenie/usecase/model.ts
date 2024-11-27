var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const usecaseSchema = new Schema(
  {
    requirementId: {type: String},
    usecases: { type: [Object]},
  },
  { timestamps: { createdAt: 'createdDate', updatedAt: 'lastModifiedDate' } }
);

const usecaseCollection = "usecase";

export { usecaseSchema, usecaseCollection };
