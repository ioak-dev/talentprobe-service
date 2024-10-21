import { authorizeApi } from "../../../middlewares";
import {
  getAllUseCases,
  createUseCase,
  updateUseCaseById,
  getUseCaseById,
  deleteUseCaseById
} from "./service";

module.exports = function (router: any) {
  router.get("/suite/:id/usecase",authorizeApi, getAllUseCases);
  router.post("/suite/:id/usecase",authorizeApi, createUseCase);
  router.put("/suite/:id/usecase/:usecaseid",authorizeApi, updateUseCaseById);
  router.delete("/suite/:id/usecase/:usecaseid",authorizeApi, deleteUseCaseById);
  router.get("/suite/:id/usecase/:usecaseid",authorizeApi, getUseCaseById);

};
