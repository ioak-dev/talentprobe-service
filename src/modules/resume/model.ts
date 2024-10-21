var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const resumeSchema = new Schema(
  {
    attachment: { type: Buffer },
    data: { type: JSON },
    fileName: { type: String },
  },
  { timestamps: true }
);

const resumeCollection = "resume";

export { resumeSchema, resumeCollection };
