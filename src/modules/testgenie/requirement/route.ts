import { authorizeApi } from "../../../middlewares";
import {
  getRequirementByApp,
  createRequirement,
  deleteRequirementById,
  updateRequirementById,
  getRequirementById,
  exportApp,
  deleteRequirement
} from "./service";


module.exports = function (router: any) {
  router.get("/:space/application/:id/requirement", authorizeApi ,getRequirementByApp);

  router.post("/:space/application/:id/requirement", authorizeApi, createRequirement);

  router.put("/:space/application/:id/requirement/:requirementid", authorizeApi, updateRequirementById);

  router.delete("/:space/application/:id/requirement/:requirementid", authorizeApi, deleteRequirementById);

  router.get("/:space/application/:id/requirement/:requirementid", authorizeApi,  getRequirementById);

  router.delete("/:space/application/:id/requirement", authorizeApi, deleteRequirement);

  router.get("/:space/application/:id", exportApp);
}

