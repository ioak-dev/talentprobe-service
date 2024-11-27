import { authorizeApi } from "../../../middlewares";
import {
  generateUsecase
} from "./service";

module.exports = function (router: any) {
  router.post("/:space/application/:id/requirement/:requirementid/usecase/generate", authorizeApi, generateUsecase);
};
