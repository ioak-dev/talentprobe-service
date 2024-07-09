var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const assessmentResponseheaderSchema = new Schema(
  {
    assessmentId: { type: String },
    email: { type: String },
    givenName: { type: String },
    lastName: { type: String },
    isSubmitted: { type: Boolean },
    score: { type: Number },
  },
  { timestamps: true }
);

const assessmentResponseheaderCollection = "assessment.responseheader";

// module.exports = mongoose.model('bookmarks', articleSchema);
export { assessmentResponseheaderSchema, assessmentResponseheaderCollection };
