import { authorizeApi } from "../../../middlewares";
import {
  createAssessmentResponseheader,
  updateAssessmentResponseheader,
  getAssessmentResponseheader,
  deleteAssessmentResponseheader,
  reloadAssessmentResponse,
  getAssessmentResponseheaderById
} from "./service";

module.exports = function (router: any) {
  router.post(
    "/assessment/:id/response",
    createAssessmentResponseheader
  );
  router.get(
    "/assessment/:id/response",
    getAssessmentResponseheader
  );
  router.get(
    "/assessment/:id/response/:responseId",
    getAssessmentResponseheaderById
  );
  router.get(
    "/assessment/:id/response/:responseId/reload",
    reloadAssessmentResponse
  );
  router.delete(
    "/assessment/:id/responseheader/:responseId",
    authorizeApi,
    deleteAssessmentResponseheader
  );
};
