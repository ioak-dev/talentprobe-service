import { authorizeApi } from "../../middlewares";
import { createAssessment, updateAssessment, getAssessment, getAssessmentById } from "./service";

const selfRealm = 100;

module.exports = function (router: any) {
  router.post("/assessment", authorizeApi, createAssessment);
  router.put("/assessment/:id", authorizeApi, updateAssessment);
  router.get("/assessment", authorizeApi, getAssessment);
  router.get("/assessment/:id", authorizeApi, getAssessmentById);
  // router.post("/auth/token", issueToken);
  // router.get("/auth/token/decode", authorizeApi, decodeToken);
  // router.post("/auth/logout", logout);
  // router.get("/auth/oa/session/:id", (req: any, res: any) =>
  //   validateSession(selfRealm, req, res)
  // );
};
