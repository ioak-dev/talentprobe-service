import { authorizeApi } from "../../middlewares";
import {
  getAllUseCase,
  createUseCase,
  updateUseCaseById,
  deleteUseCaseById,
  getUseCaseById
} from "./service";

const selfRealm = 100;

module.exports = function (router: any) {
  router.get("/suite/:suiteId/usecase", getAllUseCase);

  router.post("/suite/:suiteId/usecase",createUseCase);

  router.put("/suite/:suiteId/usecase/:id",updateUseCaseById);

  router.delete("/suite/:suiteId/usecase/:id",deleteUseCaseById);

  router.get("/suite/:suiteId/usecase/:id", getUseCaseById);
}
