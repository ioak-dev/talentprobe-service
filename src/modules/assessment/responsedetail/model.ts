var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const assessmentResponsedetailSchema = new Schema(
  {
    assessmentId: { type: String },
    type: { type: String },
    data: { type: JSON },
    pinned: { type: Boolean },
  },
  { timestamps: true }
);

const assessmentResponsedetailCollection = "assessment.assessmentResponsedetail";

// module.exports = mongoose.model('bookmarks', articleSchema);
export { assessmentResponsedetailSchema, assessmentResponsedetailCollection };
