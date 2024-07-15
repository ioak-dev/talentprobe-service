import { authorizeApi } from "../../../middlewares";
import {
  createAssessmentResponsedetail,
  updateAssessmentResponsedetail,
  getAssessmentResponsedetail,
  deleteAssessmentResponsedetail,
  getAssessmentResponsedetailByResponseId
} from "./service";

const selfRealm = 100;

module.exports = function (router: any) {
  router.get(
    "/assessment/:id/response-detail/:responseId",
    getAssessmentResponsedetailByResponseId
  )
  router.post(
    "/assessment/:id/response/:responseId",
    createAssessmentResponsedetail
  )
  router.delete(
    "/assessment/:id/question/:questionId",
    authorizeApi,
    deleteAssessmentResponsedetail
  );
  router.get("/assessment/:id/question", authorizeApi, getAssessmentResponsedetail);
};
