import { authorizeApi } from "../../../middlewares";
import{
    createTestcase,
    deleteAllTestcase,
    deleteTestcaseById,
    generateTestcase,
    getTestcase, 
    getTestcaseById,
    updateTestcaseById
} from "./service";

module.exports= function(router:any) {
    router.post("/:space/application/:id/requirement/:id/usecase/:id/testcase", authorizeApi, createTestcase);
    router.get("/:space/application/:id/requirement/:id/usecase/:id/testcase", authorizeApi, getTestcase);
    router.delete("/:space/application/:id/requirement/:id/usecase/:id/testcase", authorizeApi, deleteAllTestcase);
    router.delete("/:space/application/:id/requirement/:id/usecase/:id/testcase/:id", authorizeApi, deleteTestcaseById);
    router.get("/:space/application/:id/requirement/:id/usecase/:id/testcase/:id", authorizeApi, getTestcaseById);
    router.put("/:space/application/:id/requirement/:id/usecase/:id/testcase/:id", authorizeApi, updateTestcaseById);
    router.post("/:space/application/:id/requirement/:id/usecase/:id/testcase/generate", authorizeApi, generateTestcase )
};