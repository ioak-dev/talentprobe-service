var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const usecaseSchema = new Schema(
  {
      suiteId: { type: String },
      useCaseName: { type: String },
      description: { type: String },
  },
  { timestamps: { createdAt: 'createdDate', updatedAt: 'lastModifiedDate' } }
);

const usecaseCollection = "usecase";

export { usecaseSchema, usecaseCollection };
