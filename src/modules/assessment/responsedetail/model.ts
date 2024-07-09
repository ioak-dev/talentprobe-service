var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const assessmentResponsedetailSchema = new Schema(
  {
    responseId: { type: String },
    questionId: { type: String },
    answer: { type: String },
    isSubmitted: { type: Boolean },
    score: { type: Number },
  },
  { timestamps: true }
);

const assessmentResponsedetailCollection = "assessment.responsedetail";

// module.exports = mongoose.model('bookmarks', articleSchema);
export { assessmentResponsedetailSchema, assessmentResponsedetailCollection };
