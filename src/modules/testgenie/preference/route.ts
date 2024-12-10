import { authorizeApi } from "../../../middlewares";
import{
    addPreference,
    getPreference
} from "./service";

module.exports = function(router: any){
    router.get("/:space/preference", authorizeApi, getPreference);
    router.post("/:space/preference", authorizeApi, addPreference);
    
}