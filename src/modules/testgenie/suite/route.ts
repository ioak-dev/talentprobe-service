import { authorizeApi } from "../../../middlewares";
import {
  getAllSuite,
  createSuite,
  deleteSuiteById,
  updateSuiteById,
  getSuiteById,
  exportSuite
} from "./service";

const selfRealm = 100;

module.exports = function (router: any) {
  router.get("/suite", getAllSuite);

  router.post("/suite",createSuite);

  router.put("/suite/:id",updateSuiteById);

  router.delete("/suite/:id",deleteSuiteById);

  router.get("/suite/:id", getSuiteById);

  router.get("/export-suite/:id", exportSuite);
}

