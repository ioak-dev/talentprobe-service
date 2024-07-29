var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const resumeSchema = new Schema(
  {
    attachment: { type: [] },
    data: { type: JSON },
    filename: { type: String },
  },
  { timestamps: true }
);

const resumeCollection = "resume";

export { resumeSchema, resumeCollection };
