import { authorizeApi } from "../../../middlewares";

import {generateConsolidatedTestCases,getAllConsolidatedTestCase} from "./service";


module.exports = function (router: any) {

  router.get("/suite/:suiteId/use-case/testcase",authorizeApi, getAllConsolidatedTestCase);
  router.post("/suite/:suiteId/use-case/testcase",authorizeApi, generateConsolidatedTestCases);

};
