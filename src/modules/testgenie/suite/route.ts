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
  router.get("/suite",authorizeApi, getAllSuite);

  router.post("/suite",authorizeApi, createSuite);

  router.put("/suite/:id",authorizeApi, updateSuiteById);

  router.delete("/suite/:id",authorizeApi,deleteSuiteById);

  router.get("/suite/:id",authorizeApi, getSuiteById);

  router.get("/export-suite/:id",authorizeApi, exportSuite);
}

