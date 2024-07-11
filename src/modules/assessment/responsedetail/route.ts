import { authorizeApi } from "../../../middlewares";
import {
  createAssessmentResponsedetail,
  updateAssessmentResponsedetail,
  getAssessmentResponsedetail,
  deleteAssessmentResponsedetail,
} from "./service";

const selfRealm = 100;

module.exports = function (router: any) {
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
