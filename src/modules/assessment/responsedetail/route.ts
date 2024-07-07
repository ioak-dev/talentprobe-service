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
    "/assessment/:id/question",
    authorizeApi,
    createAssessmentResponsedetail
  );
  router.put(
    "/assessment/:id/question/:questionId",
    authorizeApi,
    updateAssessmentResponsedetail
  );
  router.delete(
    "/assessment/:id/question/:questionId",
    authorizeApi,
    deleteAssessmentResponsedetail
  );
  router.get("/assessment/:id/question", authorizeApi, getAssessmentResponsedetail);
  // router.post("/auth/token", issueToken);
  // router.get("/auth/token/decode", authorizeApi, decodeToken);
  // router.post("/auth/logout", logout);
  // router.get("/auth/oa/session/:id", (req: any, res: any) =>
  //   validateSession(selfRealm, req, res)
  // );
};
