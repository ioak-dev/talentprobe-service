var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const suiteSchema = new Schema(
  {
    name: { type: String },
  },
  { timestamps: true }
);

const suiteCollection = "suite";
export { suiteSchema, suiteCollection };
