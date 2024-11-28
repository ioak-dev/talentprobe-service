import { authorizeApi } from "../../../middlewares";
import {
  generateUsecase
} from "./service";

module.exports = function (router: any) {
  router.post("/:space/application/:id/requirement/:id/usecase/generate", authorizeApi, generateUsecase);
};
