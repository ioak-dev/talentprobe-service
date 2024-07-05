import { authorizeApi } from "../../../middlewares";
import {
  createAssessmentQuestion,
  updateAssessmentQuestion,
  getAssessmentQuestion,
  deleteAssessmentQuestion,
  generateQuestions,
} from "./service";

const selfRealm = 100;

module.exports = function (router: any) {
  router.post(
    "/assessment/:id/question",
    authorizeApi,
    createAssessmentQuestion
  );
  router.post(
    "/assessment/:id/generate-questions/:count",
    authorizeApi,
    generateQuestions
  );
  router.put(
    "/assessment/:id/question/:questionId",
    authorizeApi,
    updateAssessmentQuestion
  );
  router.delete(
    "/assessment/:id/question/:questionId",
    authorizeApi,
    deleteAssessmentQuestion
  );
  router.get("/assessment/:id/question", authorizeApi, getAssessmentQuestion);
  // router.post("/auth/token", issueToken);
  // router.get("/auth/token/decode", authorizeApi, decodeToken);
  // router.post("/auth/logout", logout);
  // router.get("/auth/oa/session/:id", (req: any, res: any) =>
  //   validateSession(selfRealm, req, res)
  // );
};
