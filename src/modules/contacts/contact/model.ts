var mongoose = require("mongoose");

const Schema = mongoose.Schema;
const contactSchema = new Schema(
  {
    givenName: { type: String },
    familyName: { type: String },
    email: { type: String },
    telephone: { type: String },
    comments: { type: String },
    topic: { type: String },
  },
  { timestamps: true }
);

const contactCollection = "contacts.contact";

// module.exports = mongoose.model('bookmarks', articleSchema);
export { contactSchema, contactCollection };
