var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const usecaseSchema = new Schema(
  {
    applicationId: {type: String},
    requirementId: {type: String},
    description: {type: String}
  },
  { timestamps: { createdAt: 'createdDate', updatedAt: 'lastModifiedDate' } }
);

const usecaseCollection = "usecase";

export { usecaseSchema, usecaseCollection };
