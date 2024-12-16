import { authorizeApi } from "../../../middlewares";
import {
  generateUsecase,
  getUsecase,
  createUsecase,
  deleteAllUsecase,
  deleteUsecaseById,
  getUsecaseById,
  updateUsecaseById
} from "./service";

module.exports = function (router: any) {
  router.get("/:space/application/:id/requirement/:id/usecase", authorizeApi, getUsecase);
  router.post("/:space/application/:id/requirement/:id/usecase", authorizeApi, createUsecase);
  router.post("/:space/application/:id/requirement/:id/usecase/generate",authorizeApi, generateUsecase);
  router.delete("/:space/application/:id/requirement/:id/usecase", authorizeApi, deleteAllUsecase);
  router.delete("/:space/application/:id/requirement/:id/usecase/:id",authorizeApi, deleteUsecaseById);
  router.get("/:space/application/:id/requirement/:id/usecase/:id", authorizeApi, getUsecaseById);
  router.put("/:space/application/:id/requirement/:id/usecase/:id", authorizeApi, updateUsecaseById);
};
