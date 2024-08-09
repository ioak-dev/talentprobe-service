import { authorizeApi } from "../../../middlewares";
import {
  getAllUseCases,
  createUseCase,
  updateUseCaseById,
  getUseCaseById,
  deleteUseCaseById
} from "./service";

module.exports = function (router: any) {
  router.get("/suite/:id/usecase", getAllUseCases);
  router.post("/suite/:id/usecase", createUseCase);
  router.put("/suite/:id/usecase/:usecaseid",updateUseCaseById);
  router.delete("/suite/:id/usecase/:usecaseid", deleteUseCaseById);
  router.get("/suite/:id/usecase/:usecaseid", getUseCaseById);

};
