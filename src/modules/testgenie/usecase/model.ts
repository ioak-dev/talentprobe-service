var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const usecaseGeminiSchema = new Schema(
  {
      description: { type: String },
  },
  { timestamps: { createdAt: 'createdDate', updatedAt: 'lastModifiedDate' } }
);

const usecaseGeminiCollection = "usecase";

export { usecaseGeminiSchema, usecaseGeminiCollection };
