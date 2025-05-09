import { authorizeApi } from "../../../middlewares";
import {
  getAllApp,
  createApp,
  deleteAppById,
  updateAppById,
  getAppById
} from "./service";

module.exports = function (router: any) {
  router.get("/:space/application", authorizeApi ,getAllApp);

  router.post("/:space/application", authorizeApi, createApp);

  router.put("/:space/application/:id", authorizeApi, updateAppById);

  router.delete("/:space/application/:id", authorizeApi, deleteAppById);

  router.get("/:space/application/:id", authorizeApi,  getAppById);
}

