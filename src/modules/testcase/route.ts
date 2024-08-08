import { authorizeApi } from "../../middlewares";
import {
    getAllTestCases
  } from "./service";
  
  
  module.exports = function (router: any) {
    router.get(
      "/suite/:suiteId/usecase/:usecaseId/testcase",
      getAllTestCases
    );
}