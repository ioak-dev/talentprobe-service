import { authorizeApi } from "../../middlewares";
import {
  getResume,
  getResumeById,
  scanResume
} from "./service";
import multer from "multer";
var upload = multer();


const selfRealm = 100;

module.exports = function (router: any) {
  router.get("/resume", authorizeApi, getResume);
  router.get("/resume/:id", getResumeById);
  router.post("/resume", upload.single("file"), scanResume);

};
