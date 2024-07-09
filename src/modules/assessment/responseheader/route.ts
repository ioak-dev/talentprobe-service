import { authorizeApi } from "../../../middlewares";
import {
  createAssessmentResponseheader,
  updateAssessmentResponseheader,
  getAssessmentResponseheader,
  deleteAssessmentResponseheader,
} from "./service";

module.exports = function (router: any) {
  router.post(
    "/assessment/:id/responseheader",
    authorizeApi,
    createAssessmentResponseheader
  );
  router.delete(
    "/assessment/:id/responseheader/:responseId",
    authorizeApi,
    deleteAssessmentResponseheader
  );
};