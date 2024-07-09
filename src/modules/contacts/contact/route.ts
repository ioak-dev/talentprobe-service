import { authorizeApi } from "../../../middlewares";
import {
  createContact,
  updateContact,
  getContact,
  getContactById,
  deleteContact,
} from "./service";

const selfRealm = 100;

module.exports = function (router: any) {
  router.post("/contact", createContact);
  router.put("/contact/:id", updateContact);
  router.delete("/contact/:id", deleteContact);
  router.get("/contact", getContact);
  router.get("/contact/:id", getContactById);
};
