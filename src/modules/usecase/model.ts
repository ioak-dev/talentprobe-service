var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const useCasechema = new Schema(
  {
    suiteId : { type: String },
    usecaseName : { type: String },
    description : { type: String },
  },
  { timestamps: true }
);

const useCaseCollection = "usecase";
export { useCasechema,useCaseCollection };
