import { authorizeApi } from "../../../middlewares";
import {
  createAssessmentResponseheader,
  updateAssessmentResponseheader,
  getAssessmentResponseheader,
  deleteAssessmentResponseheader,
  getAssessmentResponseheaderByResponseId
} from "./service";

module.exports = function (router: any) {
  router.post(
    "/assessment/:id/response",
    createAssessmentResponseheader
  );
  router.get(
    "/assessment/:id/response/:responseId",
    getAssessmentResponseheaderByResponseId
  );
  router.delete(
    "/assessment/:id/responseheader/:responseId",
    authorizeApi,
    deleteAssessmentResponseheader
  );
};
