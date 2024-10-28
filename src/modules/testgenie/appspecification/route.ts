import { authorizeApi } from "../../../middlewares";
import {
  createAppUseCase,
  getAppUseCases,
  createAppTestCase
} from "./service";

const selfRealm = 100;

module.exports = function (router: any) {
  router.get("/suite/:id/appusecase",getAppUseCases);
  router.post("/suite/:id/appusecase", createAppUseCase);
  router.post("/suite/:id/appusecase/:usecaseid/testcase", createAppTestCase);
}

