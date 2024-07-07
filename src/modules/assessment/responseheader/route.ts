import { authorizeApi } from "../../../middlewares";
import {
  createAssessmentResponseheader,
  updateAssessmentResponseheader,
  getAssessmentResponseheader,
  deleteAssessmentResponseheader
} from "./service";

const selfRealm = 100;

module.exports = function (router: any) {
  router.post(
    "/assessment/:id/question",
    authorizeApi,
    createAssessmentResponseheader
  );
  router.put(
    "/assessment/:id/question/:questionId",
    authorizeApi,
    updateAssessmentResponseheader
  );
  router.delete(
    "/assessment/:id/question/:questionId",
    authorizeApi,
    deleteAssessmentResponseheader
  );
  router.get("/assessment/:id/question", authorizeApi, getAssessmentResponseheader);
  // router.post("/auth/token", issueToken);
  // router.get("/auth/token/decode", authorizeApi, decodeToken);
  // router.post("/auth/logout", logout);
  // router.get("/auth/oa/session/:id", (req: any, res: any) =>
  //   validateSession(selfRealm, req, res)
  // );
};
