var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const appusecaseSchema = new Schema(
  {
      suiteId: { type: String },
      useCaseName: { type: String },
      description: { type: String },
      priority: { type:String },
      category: { type:String }
  },
  { timestamps: { createdAt: 'createdDate', updatedAt: 'lastModifiedDate' } }
);

const appusecaseCollection = "appusecase";

export { appusecaseSchema, appusecaseCollection };
