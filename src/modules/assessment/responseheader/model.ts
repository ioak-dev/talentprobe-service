var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const assessmentResponseheaderSchema = new Schema(
  {
    assessmentId: { type: String },
    type: { type: String },
    data: { type: JSON },
    pinned: { type: Boolean },
  },
  { timestamps: true }
);

const assessmentResponseheaderCollection = "assessment.assessmentResponseheader";

// module.exports = mongoose.model('bookmarks', articleSchema);
export { assessmentResponseheaderSchema, assessmentResponseheaderCollection };
