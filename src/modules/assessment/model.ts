var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const assessmentSchema = new Schema(
  {
    name: { type: String },
    jobDescription: { type: String },
    duration: { type: String },
    status: { type: String },
    lastRecommendationNumber: { type: Number },
    skillSet: { type: Array },
  },
  { timestamps: true }
);

const assessmentCollection = "assessment";

// module.exports = mongoose.model('bookmarks', articleSchema);
export { assessmentSchema, assessmentCollection };
