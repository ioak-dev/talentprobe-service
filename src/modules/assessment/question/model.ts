var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const assessmentQuestionSchema = new Schema(
  {
    assessmentId: { type: String },
    type: { type: String },
    data: { type: JSON },
    pinned: { type: Boolean },
  },
  { timestamps: true }
);

const assessmentQuestionCollection = "assessment.question";

// module.exports = mongoose.model('bookmarks', articleSchema);
export { assessmentQuestionSchema, assessmentQuestionCollection };
